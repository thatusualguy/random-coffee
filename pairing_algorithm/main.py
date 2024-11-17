from fastapi import FastAPI, HTTPException
from RecAlgo import cluster_users, get_recommendations_for_user_kmeans, unique_interests, users_data
from typing import Optional
import uvicorn
from db import get_viewed_users, add_viewed_user

app = FastAPI()

# Устанавливаем количество кластеров вручную и выполняем кластеризацию пользователей
n_clusters = 4
cluster_users(users_data, unique_interests, n_clusters)

@app.get("/recommendations/{user_id}", response_model=Optional[dict])
async def get_recommendations(user_id: int):
    # Получаем рекомендации для пользователя по ID
    recommendations = get_recommendations_for_user_kmeans(user_id)

    if not recommendations:
        raise HTTPException(status_code=404, detail="Рекомендаций для пользователя нет")

    # Получаем список уже просмотренных пользователей
    viewed_users = set(get_viewed_users(user_id))

    # Находим первого подходящего пользователя, который ещё не был просмотрен
    for candidate_id, score in sorted(recommendations, key=lambda x: x[1], reverse=True):
        if candidate_id not in viewed_users:
            # Добавляем выбранного пользователя в список просмотренных
            add_viewed_user(user_id, candidate_id)
            # Формируем ответ в формате JSON
            response = {"recommended_user_id": candidate_id, "score": round(score, 2)}
            return response

    # Если нет подходящих пользователей
    raise HTTPException(status_code=404, detail="Нет доступных рекомендаций для пользователя")

if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)
