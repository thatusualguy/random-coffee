include .env
export $(shell sed 's/=.*//' .env)

MIGRATIONS_DIR := ./migrations
CMD_DIR := ./cmd

DB_CONN := "host=${DB_HOST} port=${DB_PORT} user=${DB_USER} password=${DB_PASSWORD} dbname=${DB_NAME} sslmode=disable"

migrate_up:
	echo ${DB_CONN}
	goose -dir $(MIGRATIONS_DIR) postgres $(DB_CONN) up

migrate_down:
	goose -dir $(MIGRATIONS_DIR) postgres $(DB_CONN) down