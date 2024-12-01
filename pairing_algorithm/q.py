import psycopg2

# Подключение к базе данных
conn = psycopg2.connect(
    host="127.0.0.1",       # Хост базы данных
    user="postgres",        # Имя пользователя
    password="New_proger_4", # Пароль
    dbname="claster_users"  # Имя базы данных (правильный ключ)
)

cursor = conn.cursor()

# Выполнение SQL-запроса
cursor.execute("SELECT * FROM viewed_users WHERE user_id = %s;", (5,))
result = cursor.fetchall()
print("Просмотренные пользователи:", result)

# Очистка записи
cursor.execute("DELETE FROM viewed_users WHERE user_id = %s;", (5,))
conn.commit()
print("Записи успешно удалены.")

# Закрытие соединения
cursor.close()
conn.close()
