import psycopg2
from psycopg2 import sql
from config import host, user, password, db_name


def create_user_clusters_table():
    # Устанавливаем соединение с базой данных
    try:
        connection = psycopg2.connect(
            host=host,
            user=user,
            password=password,
            database=db_name
        )
        cursor = connection.cursor()

        # SQL-запрос для создания таблицы
        create_table_query = """
        CREATE TABLE IF NOT EXISTS user_clusters_info (
            user_id SERIAL PRIMARY KEY,
            cluster_id INT NOT NULL,
            UNIQUE(user_id)
        );
        """

        # Выполнение запроса
        cursor.execute(create_table_query)
        connection.commit()
        print("Таблица user_clusters_info успешно создана.")

    except Exception as error:
        print(f"Ошибка при создании таблицы: {error}")
    finally:
        # Закрываем соединение
        if connection:
            cursor.close()
            connection.close()


if __name__ == "__main__":
    create_user_clusters_table()
