package repository

import (
	"fmt"
	"service.user/internal/models"
	"strings"
)

func buildQuery(fields models.UserFields, user *models.User) (string, []any) {
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
		columns = append(columns, "users.available_dates")
		dest = append(dest, &user.AvailableDates)
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
