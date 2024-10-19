from datetime import datetime, timedelta
user_data = {
    1: {
        'станции_метро': {'Станция1', 'Станция2'},
        'предпочтительные_дни': {'Понедельник', 'Среда'},
        'предпочтительное_время': {'18:00-20:00', '20:00-22:00'},
        'интересы': {'Спорт', 'Музыка', 'Чтение'}
    },
    2: {
        'станции_метро': {'Станция2', 'Станция3'},
        'предпочтительные_дни': {'Среда', 'Пятница'},
        'предпочтительное_время': {'18:00-20:00'},
        'интересы': {'Спорт', 'Фильмы', 'Музыка'}
    },
    3: {
        'станции_метро': {'Станция3', 'Станция4'},
        'предпочтительные_дни': {'Понедельник', 'Четверг'},
        'предпочтительное_время': {'20:00-22:00'},
        'интересы': {'Готовка', 'Музыка', 'Чтение'}
    },
    4: {
        'станции_метро': {'Станция1'},
        'предпочтительные_дни': {'Понедельник', 'Среда'},
        'предпочтительное_время': {'18:00-20:00'},
        'интересы': {'Спорт', 'Чтение'}
    },
    5: {
        'станции_метро': {'Станция2', 'Станция3'},
        'предпочтительные_дни': {'Вторник', 'Пятница'},
        'предпочтительное_время': {'18:00-20:00', '20:00-22:00'},
        'интересы': {'Музыка', 'Фильмы', 'Готовка'}
    }
}




# Функция для проверки пересечения временных диапазонов на час или больше
def has_time_overlap(time_ranges1, time_ranges2, min_overlap_minutes=60):
    def parse_time_range(time_range):
        start_str, end_str = time_range.split('-')
        return datetime.strptime(start_str, '%H:%M'), datetime.strptime(end_str, '%H:%M')

    min_overlap = timedelta(minutes=min_overlap_minutes)

    for range1 in time_ranges1:
        start1, end1 = parse_time_range(range1)
        for range2 in time_ranges2:
            start2, end2 = parse_time_range(range2)

            latest_start = max(start1, start2)
            earliest_end = min(end1, end2)
            overlap = earliest_end - latest_start

            if overlap >= min_overlap:
                return True
    return False



def has_common_preferences(target_user, candidate_user):

    if not target_user['станции_метро'].intersection(candidate_user['станции_метро']):
        return False


    if not target_user['предпочтительные_дни'].intersection(candidate_user['предпочтительные_дни']):
        return False


    if not has_time_overlap(target_user['предпочтительное_время'], candidate_user['предпочтительное_время']):
        return False

    return True



def jaccard_coefficient(set1, set2):
    intersection = set1.intersection(set2)
    union = set1.union(set2)
    if not union:
        return 0
    return len(intersection) / len(union)


# Получение рекомендаций для заданного пользователя
def get_recommendations_for_user(target_user_id, user_data):
    recommendations = []
    target_user = user_data[target_user_id]

    for candidate_id, candidate_data in user_data.items():
        if candidate_id == target_user_id:
            continue

        # Проверка основных условий (станция метро, дни, время)
        if not has_common_preferences(target_user, candidate_data):
            continue

        # Расчет коэффициента Жаккара для интересов
        jaccard_coef = jaccard_coefficient(target_user['интересы'], candidate_data['интересы'])

        if jaccard_coef > 0:
            recommendations.append((candidate_id, jaccard_coef))

    recommendations.sort(reverse=True, key=lambda item: item[1])

    return recommendations



target_user_id = 1  # Идентификатор пользователя, для которого получаем рекомендации
recommendations = get_recommendations_for_user(target_user_id, user_data)

for candidate_id, coef in recommendations:
    print(f"Пользователь {candidate_id} подходит с коэффициентом Жаккара: {coef:.2f}")