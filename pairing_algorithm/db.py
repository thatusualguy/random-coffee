import psycopg2
from psycopg2 import sql
from config import host, user, password, db_name
from typing import List
import json
def connect_db():
    """Функция для подключения к базе данных."""

    return psycopg2.connect(
        host=host,
        user=user,
        password=password,
        database=db_name
    )


def get_viewed_users(user_id: int) -> List[int]:
    """
    Получить список просмотренных пользователей для заданного user_id из user_clusters_info.
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT viewed_user_ids FROM user_clusters_info WHERE user_id = %s;", (user_id,))
        result = cursor.fetchone()

        if result and result[0]:
            if isinstance(result[0], str):  # Если данные хранятся как JSON
                return json.loads(result[0])  # Парсим JSON-строку в Python-объект
            elif isinstance(result[0], list):  # Если уже список
                return result[0]
        return []
    finally:
        cursor.close()
        conn.close()



def add_viewed_user(user_id: int, viewed_user_id: int):
    """
    Добавить пользователя в список просмотренных для заданного user_id в user_clusters_info.
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        # Получаем текущий список просмотренных пользователей
        cursor.execute("SELECT viewed_user_ids FROM user_clusters_info WHERE user_id = %s;", (user_id,))
        result = cursor.fetchone()

        if result and result[0]:
            # Если данные уже в формате JSON
            if isinstance(result[0], str):
                viewed_users_list = set(json.loads(result[0]))  # Парсим строку JSON в список
            elif isinstance(result[0], list):  # Если данные уже список
                viewed_users_list = set(result[0])
            else:
                raise ValueError(f"Unexpected format for viewed_user_ids: {result[0]}")
        else:
            viewed_users_list = set()  # Если данных нет, создаём пустое множество

        # Добавляем нового пользователя в множество
        viewed_users_list.add(viewed_user_id)

        # Преобразуем обратно в JSON
        updated_viewed_users = json.dumps(list(viewed_users_list))  # Сериализуем в JSON-строку

        # Обновляем запись
        cursor.execute(
            "UPDATE user_clusters_info SET viewed_user_ids = %s WHERE user_id = %s;",
            (updated_viewed_users, user_id)
        )

        conn.commit()  # Фиксируем транзакцию
        print(f"Successfully updated viewed_user_ids for user_id={user_id} with {viewed_user_id}")
    except Exception as e:
        print(f"Error updating viewed_user_ids for user_id={user_id}: {e}")
        conn.rollback()  # Откат транзакции при ошибке
    finally:
        cursor.close()
        conn.close()


def get_all_users_from_db():
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT user_id, cluster_id, viewed_user_ids, interests, preferred_days FROM user_clusters_info")
        rows = cursor.fetchall()

        users_data = []
        for row in rows:
            user_id = row[0]
            cluster_id = row[1]
            viewed_user_ids = row[2] if isinstance(row[2], list) else json.loads(row[2]) if row[2] else []  # Проверяем формат
            interests = row[3] if isinstance(row[3], list) else json.loads(row[3]) if row[3] else []  # Проверяем формат
            preferred_days = row[4] if isinstance(row[4], list) else json.loads(row[4]) if row[4] else []  # Проверяем формат

            users_data.append({
                "id": user_id,
                "cluster_id": cluster_id,
                "viewed_user_ids": viewed_user_ids,
                "interests": set(interests),  # Преобразуем в множества для обработки
                "preferred_days": set(preferred_days)  # Преобразуем в множества для обработки
            })

        cursor.close()
        conn.close()
        return users_data

    except Exception as e:
        print(f"Ошибка при извлечении пользователей: {e}")
        return []

# Функция для сохранения кластеров в базе данных с проверкой на дублирование
def save_clusters_to_db(users_data, clusters):
    conn = connect_db()
    cursor = conn.cursor()
    try:
        for user, cluster in zip(users_data, clusters):
            user_id = user['id']
            # Преобразование типа cluster в int
            cluster_id = int(cluster)  # Преобразование в стандартный тип int
            cursor.execute("""
                INSERT INTO user_clusters_info (user_id, cluster_id)
                VALUES (%s, %s)
                ON CONFLICT (user_id) DO UPDATE SET cluster_id = EXCLUDED.cluster_id;
            """, (user_id, cluster_id))
        conn.commit()
    except Exception as e:
        print(f"Ошибка при сохранении кластеров: {e}")
    finally:
        cursor.close()
        conn.close()

# Функция для извлечения кластера пользователя из базы данных
def get_user_cluster(user_id):
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT cluster_id FROM user_clusters_info WHERE user_id = %s", (user_id,))
        result = cursor.fetchone()
        return result[0] if result else None
    except Exception as error:
        print(f"Ошибка при получении кластера пользователя: {error}")
        return None
    finally:
        cursor.close()
        conn.close()





