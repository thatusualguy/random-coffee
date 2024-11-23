import numpy as np
from sklearn.cluster import KMeans
from sklearn.decomposition import TruncatedSVD
from sklearn.preprocessing import StandardScaler
from sklearn.metrics.pairwise import cosine_similarity
import warnings
from db import save_clusters_to_db, get_user_cluster, get_all_users_from_db
import json


# Функция для создания матрицы предпочтений
def create_preference_matrix(users_data, unique_interests):
    """
    Создает матрицу предпочтений на основе интересов пользователей.
    """
    matrix = np.zeros((len(users_data), len(unique_interests)))
    for i, user in enumerate(users_data):
        for j, interest in enumerate(unique_interests):
            if interest in user['interests']:
                matrix[i, j] = 1
    return matrix


def has_intersection(user1, user2):
    """
    Проверяет пересечение дней предпочтений между двумя пользователями.
    """
    return not user1['preferred_days'].isdisjoint(user2['preferred_days'])


def cluster_users(n_clusters=4):
    """
    Загружает пользователей из базы, рассчитывает кластеры и сохраняет результаты.
    """
    # Получение данных пользователей из базы данных
    users_data = get_all_users_from_db()

    # Генерация списка уникальных интересов
    unique_interests = list(set(interest for user in users_data for interest in user["interests"]))

    # Создаем матрицу предпочтений
    preference_matrix = create_preference_matrix(users_data, unique_interests)

    # Стандартизация матрицы предпочтений
    scaler = StandardScaler()
    preference_matrix = scaler.fit_transform(preference_matrix)

    # Кластеризация KMeans
    kmeans = KMeans(n_clusters=n_clusters, random_state=0)
    clusters = kmeans.fit_predict(preference_matrix)

    # Сохранение кластеров в базе данных
    save_clusters_to_db(users_data, clusters)

    # Группировка пользователей по кластерам (для анализа)
    cluster_groups = {i: [] for i in range(n_clusters)}
    for i, user in enumerate(users_data):
        user['cluster'] = clusters[i]
        cluster_groups[clusters[i]].append(user)

    # Упорядочивание пользователей в кластерах
    for cluster_id, users in cluster_groups.items():
        with_intersection = []
        without_intersection = []
        for user in users:
            if any(has_intersection(user, other_user) for other_user in users if other_user != user):
                with_intersection.append(user)
            else:
                without_intersection.append(user)
        cluster_groups[cluster_id] = with_intersection + without_intersection

    # Вывод результатов кластеризации
    for cluster_id, users in cluster_groups.items():
        user_ids = [user['id'] for user in users]
        print(f"Кластер {cluster_id}: Пользователи {user_ids}")

    return clusters


def jaccard_coefficient(set1, set2):
    """
    Рассчитывает коэффициент Жаккара между двумя множествами.
    """
    intersection = set1.intersection(set2)
    union = set1.union(set2)
    return len(intersection) / len(union) if union else 0


def calculate_similarity(user_matrix, target_user_index, n_factors=5):
    """
    Вычисляет схожесть между пользователями с помощью ALS или косинусной близости.
    """
    try:
        svd = TruncatedSVD(n_components=n_factors)
        user_factors = svd.fit_transform(user_matrix)
        target_user_vector = user_factors[target_user_index]
        similarities = []
        for i, user_vector in enumerate(user_factors):
            if i != target_user_index:
                similarity = cosine_similarity([target_user_vector], [user_vector])[0][0]
                similarities.append((i, similarity))
        return similarities
    except Exception as e:
        warnings.warn("Проблемы с TruncatedSVD, используется косинусная близость.")
        similarities = cosine_similarity(user_matrix)
        return [(i, similarities[target_user_index][i]) for i in range(len(similarities)) if i != target_user_index]


def get_recommendations_for_user_kmeans(target_user_id, n_clusters=3):
    """
    Генерирует рекомендации для пользователя на основе его кластера.
    """
    # Загрузка пользователей из базы
    users_data = get_all_users_from_db()

    # Получение целевого пользователя
    target_user = next((user for user in users_data if user['id'] == target_user_id), None)
    if not target_user:
        print(f"Пользователь с ID {target_user_id} не найден.")
        return []

    # Проверка наличия кластера
    target_cluster = get_user_cluster(target_user_id)
    if target_cluster is None:
        print(f"Кластер для пользователя с ID {target_user_id} не найден.")
        return []

    target_user['cluster_id'] = target_cluster  # Добавляем cluster_id к целевому пользователю

    # Пользователи в том же кластере
    same_cluster_users = [user for user in users_data if user.get('cluster_id') == target_cluster and user['id'] != target_user_id]

    if not same_cluster_users:
        print(f"Нет пользователей в кластере {target_cluster}, отличных от пользователя {target_user_id}.")
        return []

    # Создание матрицы предпочтений
    unique_interests = list(set(interest for user in same_cluster_users for interest in user['interests']))
    preference_matrix = create_preference_matrix(same_cluster_users + [target_user], unique_interests)

    # Расчет схожести
    als_similarities = calculate_similarity(preference_matrix, len(same_cluster_users))

    # Комбинирование метрик
    recommendations = []
    for candidate_index, als_similarity in als_similarities:
        candidate_user = same_cluster_users[candidate_index]
        jaccard_coef = jaccard_coefficient(set(target_user['interests']), set(candidate_user['interests']))
        combined_score = 0.5 * jaccard_coef + 0.5 * als_similarity
        recommendations.append((candidate_user['id'], combined_score))

    # Сортировка рекомендаций
    recommendations.sort(reverse=True, key=lambda item: item[1])
    return recommendations