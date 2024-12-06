from fastapi import FastAPI, HTTPException, Request
from RecAlgo import cluster_users, get_recommendations_for_user_kmeans
from typing import Optional
import uvicorn
from db import get_viewed_users, add_viewed_user, save_clusters_to_db, get_all_users_from_db

app = FastAPI()

# Устанавливаем количество кластеров
n_clusters = 4

@app.post("/add_user")
async def add_user(request: Request):
    try:
        user_data = await request.json()
        user_id = user_data.get('id')
        interests = set(user_data.get('interests', []))
        preferred_days = set(user_data.get('preferred_days', []))

        if user_id is None or not interests or not preferred_days:
            raise HTTPException(status_code=400, detail="Неверный формат данных")

        # Формируем нового пользователя
        new_user = {
            'id': user_id,
            'interests': list(interests),
            'preferred_days': list(preferred_days)
        }

        # Вызываем кластеризацию с новым пользователем
        cluster_users(n_clusters=4, new_user=new_user)

        return {"detail": f"Пользователь {user_id} добавлен и кластеры обновлены."}

    except Exception as e:
        print(f"Ошибка обработки данных пользователя: {e}")
        raise HTTPException(status_code=500, detail="Ошибка обработки данных пользователя")


@app.get("/recommendations/{user_id}", response_model=Optional[dict])
async def get_recommendations(user_id: int):
    """
    Получение рекомендаций для пользователя по ID.
    """
    try:
        # Получаем рекомендации
        recommendations = get_recommendations_for_user_kmeans(user_id)

        if not recommendations:
            raise HTTPException(status_code=404, detail="Рекомендаций для пользователя нет")

        # Получаем список просмотренных пользователей
        viewed_users = set(get_viewed_users(user_id))

        # Находим первого кандидата, который еще не был просмотрен
        for candidate_id, score in sorted(recommendations, key=lambda x: x[1], reverse=True):
            if candidate_id not in viewed_users:
                # Добавляем выбранного пользователя в список просмотренных
                add_viewed_user(user_id, candidate_id)

                # Проверяем, что данные обновились
                updated_viewed_users = set(get_viewed_users(user_id))
                if candidate_id not in updated_viewed_users:
                    raise HTTPException(status_code=500, detail="Ошибка обновления списка просмотренных пользователей")

                # Возвращаем рекомендации
                response = {"recommended_user_id": candidate_id, "score": round(score, 2)}
                return response

        # Если все кандидаты уже просмотрены
        raise HTTPException(status_code=404, detail="Нет доступных рекомендаций для пользователя")

    except Exception as e:
        print(f"Ошибка при получении рекомендаций: {e}")
        raise HTTPException(status_code=500, detail="Ошибка при обработке рекомендаций")


if __name__ == "__main__":
    
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload=True)

