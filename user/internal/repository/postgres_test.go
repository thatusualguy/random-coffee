package repository

import (
	"context"
	"github.com/stretchr/testify/assert"
	"os"
	"service.user/internal/models"
	"service.user/pkg/config"
	"service.user/pkg/logger"
	"service.user/pkg/storage"
	"testing"
)

func TestCreateUser(t *testing.T) {
	originalDir, _ := os.Getwd()
	defer os.Chdir(originalDir)

	// Меняем рабочую директорию на нужную
	os.Chdir("D:\\Programming\\Go\\random-coffee\\user")
	cfg := config.MustLoad()
	pool := storage.MustConnect()
	defer pool.Close()
	log := logger.SetupLogger(cfg.Env)
	repo := NewRepository(pool, log)
	user := models.User{
		Name:           "Alex",
		Email:          "testemail@main.ru",
		HashedPassword: "somepass",
		RefreshToken:   "sometoken",
		Interests:      []int64{1, 2, 3},
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
	query = `DELETE FROM users WHERE id=$1`
	pool.Exec(context.Background(), query, id)
}

func TestGetInterests(t *testing.T) {
	const LenOfInterests = 40
	originalDir, _ := os.Getwd()
	defer os.Chdir(originalDir)

	// Меняем рабочую директорию на нужную
	os.Chdir("D:\\Programming\\Go\\random-coffee\\user")
	cfg := config.MustLoad()
	pool := storage.MustConnect()
	defer pool.Close()
	log := logger.SetupLogger(cfg.Env)
	repo := NewRepository(pool, log)
	interests, err := repo.GetInterests(context.Background())
	if err != nil {
		assert.Fail(t, err.Error())
	}
	assert.NotEmpty(t, interests)
	assert.Equal(t, LenOfInterests, len(interests))
}
