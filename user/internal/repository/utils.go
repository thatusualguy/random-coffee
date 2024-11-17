package repository

import (
	"fmt"
	"service.user/internal/models"
	"strings"
)

func buildGetQuery(fields models.UserFields, user *models.User) (string, []any) {
	columns := []string{}
	dest := []any{}

	// Базовые поля из таблицы users
	if fields.Name {
		columns = append(columns, "users.name")
		dest = append(dest, &user.Name)
	}
	if fields.Email {
		columns = append(columns, "users.email")
		dest = append(dest, &user.Email)
	}
	if fields.RefreshToken {
		columns = append(columns, "users.refresh_token")
		dest = append(dest, &user.RefreshToken)
	}
	if fields.HashedPassword {
		columns = append(columns, "users.hashed_password")
		dest = append(dest, &user.HashedPassword)
	}
	if fields.AvailableDates {
		columns = append(columns, `(SELECT ARRAY_AGG(extract(epoch from ad.available_date)) FROM available_dates ad WHERE ad.user_id = users.id) AS available_dates`)
		dest = append(dest, &user.AvailableDates)
	}
	if fields.TgHandle {
		columns = append(columns, "users.tg_handle")
		dest = append(dest, &user.TgHandle)
	}
	var query string
	if fields.Interests {
		// Получаем только ID интересов
		if len(columns) > 0 {
			query = fmt.Sprintf(`
                SELECT %s, 
                    (SELECT ARRAY_AGG(ui.interest_id) 
                     FROM user_interests ui 
                     WHERE ui.user_id = users.id) AS interests
                FROM users 
                WHERE users.id = $1`,
				strings.Join(columns, ", "))
		} else {
			query = `
                SELECT (SELECT ARRAY_AGG(ui.interest_id) 
                       FROM user_interests ui 
                       WHERE ui.user_id = users.id) AS interests
                FROM users 
                WHERE users.id = $1`
		}
		dest = append(dest, &user.Interests)
	} else if len(columns) > 0 {
		query = fmt.Sprintf("SELECT %s FROM users WHERE id = $1", strings.Join(columns, ", "))
	} else {
		query = "SELECT name, email, hashed_password, refresh_token, dates FROM users WHERE id = $1"
	}

	return query, dest
}

func buildUpdateQuery(fields models.UserFields, updates models.User, userID int64) (string, []interface{}) {
	query := "UPDATE users SET "
	updateClauses := make([]string, 0)
	params := make([]interface{}, 0)
	paramCount := 1

	if fields.Name {
		updateClauses = append(updateClauses, fmt.Sprintf("name = $%d", paramCount))
		params = append(params, updates.Name)
		paramCount++
	}
	if fields.Email {
		updateClauses = append(updateClauses, fmt.Sprintf("email = $%d", paramCount))
		params = append(params, updates.Email)
		paramCount++
	}
	if fields.Interests {
		updateClauses = append(updateClauses, fmt.Sprintf("interests = $%d", paramCount))
		params = append(params, updates.Interests)
		paramCount++
	}
	if fields.RefreshToken {
		updateClauses = append(updateClauses, fmt.Sprintf("refresh_token = $%d", paramCount))
		params = append(params, updates.RefreshToken)
		paramCount++
	}
	if fields.HashedPassword {
		updateClauses = append(updateClauses, fmt.Sprintf("hashed_password = $%d", paramCount))
		params = append(params, updates.HashedPassword)
		paramCount++
	}
	if fields.AvailableDates {
		updateClauses = append(updateClauses, fmt.Sprintf("available_dates = $%d", paramCount))
		params = append(params, updates.AvailableDates)
		paramCount++
	}
	if fields.TgHandle {
		updateClauses = append(updateClauses, fmt.Sprintf("tg_handle = $%d", paramCount))
		params = append(params, updates.AvailableDates)
		paramCount++
	}

	if len(updateClauses) == 0 {
		return "", nil
	}

	query += strings.Join(updateClauses, ", ")
	query += fmt.Sprintf(" WHERE id = $%d RETURNING id", paramCount)
	params = append(params, userID) // Добавляем ID для WHERE clause

	return query, params
}
