package repository

import (
	"context"
	"errors"
	"fmt"
	"github.com/jackc/pgx/v5"
	"github.com/stretchr/testify/assert"
	"os"
	"service.user/internal/models"
	"service.user/pkg/config"
	"service.user/pkg/storage"
	"testing"
)

func TestCreateUser(t *testing.T) {
	originalDir, _ := os.Getwd()
	defer os.Chdir(originalDir)

	// Меняем рабочую директорию на нужную
	os.Chdir("/Users/aleksejmetlusko/GolandProjects/random-coffee/user")
	_ = config.MustLoad()
	pool := storage.MustConnect()
	defer pool.Close()
	repo := NewRepository(pool)
	user := models.User{
		Name:           "Alex",
		Email:          "test1email@main.ru",
		HashedPassword: "somepass",
		RefreshToken:   "sometoken",
		Interests:      []int64{1, 2, 3},
		AvailableDates: []int64{1730186776, 32840923840, 1283129830},
	}
	id, err := repo.CreateUser(context.Background(), user)
	if err != nil {
		assert.Fail(t, err.Error())
	}

	//check that user correctly add to database
	var testUser models.User
	var interests []int64
	query := `SELECT name, email, hashed_password, refresh_token FROM users WHERE id=$1`
	err = pool.QueryRow(context.Background(), query, id).Scan(&testUser.Name, &testUser.Email, &testUser.HashedPassword, &testUser.RefreshToken)

	query = `SELECT interest_id FROM user_interests WHERE user_id=$1`
	rows, err := pool.Query(context.Background(), query, id)
	var interest int64
	for rows.Next() {
		rows.Scan(&interest)
		interests = append(interests, interest)
	}
	testUser.Interests = interests
	assert.Equal(t, user, testUser)

	//delete row
	queries := []string{
		`DELETE FROM user_interests WHERE user_id=$1`,
		`DELETE FROM available_dates WHERE user_id=$1`,
		`DELETE FROM users WHERE id=$1`,
	}
	for _, query := range queries {
		_, err = pool.Exec(context.Background(), query, id)
		if err != nil {
			assert.Fail(t, err.Error())
		}
	}

}

func TestGetInterests(t *testing.T) {
	const LenOfInterests = 40
	_ = config.MustLoad()
	originalDir, _ := os.Getwd()
	defer os.Chdir(originalDir)

	// Меняем рабочую директорию на нужную
	os.Chdir("/Users/aleksejmetlusko/GolandProjects/random-coffee/user")
	pool := storage.MustConnect()
	defer pool.Close()
	repo := NewRepository(pool)
	interests, err := repo.GetInterests(context.Background())
	if err != nil {
		assert.Fail(t, err.Error())
	}
	assert.NotEmpty(t, interests)
	assert.Equal(t, LenOfInterests, len(interests))
}

func TestPostgresImpl_GetUserByID(t *testing.T) {
	originalDir, _ := os.Getwd()
	defer os.Chdir(originalDir)

	// Меняем рабочую директорию на нужную
	os.Chdir("/Users/aleksejmetlusko/GolandProjects/random-coffee/user")
	_ = config.MustLoad()
	pool := storage.MustConnect()
	defer pool.Close()
	repo := NewRepository(pool)
	user, err := repo.GetUserByID(context.Background(), 1, models.UserFields{
		Name:           true,
		HashedPassword: true,
		Interests:      true,
	})

	if err != nil {
		if !errors.Is(err, pgx.ErrNoRows) {
			assert.Fail(t, err.Error())
		}
	}
	fmt.Println(user)
}
