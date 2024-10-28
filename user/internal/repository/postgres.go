package repository

import (
	"context"
	"errors"
	"fmt"
	"github.com/jackc/pgx/v5"
	"github.com/jackc/pgx/v5/pgxpool"
	"log/slog"
	"service.user/internal/models"
)

type IRepository interface {
	CreateUser(ctx context.Context, user models.User) (int64, error)
	GetUserByID(ctx context.Context, userID int64, fields []string) (models.User, error)
	UpdateUserByID(ctx context.Context, userID int64, user models.User) bool
	FindUserByEmail(ctx context.Context, email string) int64
	GetInterests(ctx context.Context) ([]models.Interest, error)
}

type PostgresImpl struct {
	log  *slog.Logger
	pool *pgxpool.Pool
}

func (r PostgresImpl) GetInterests(ctx context.Context) ([]models.Interest, error) {
	op := "repository.PostgresImpl.GetInterests"
	var interests []models.Interest
	query := `SELECT id, name FROM interests`
	rows, err := r.pool.Query(ctx, query)
	defer rows.Close()
	if err != nil {
		return interests, errors.New(fmt.Sprintf("failed to get interests. Err: %s op:%s", err.Error(), op))
	}
	var interest models.Interest
	_, err = pgx.ForEachRow(rows, []any{&interest.ID, &interest.Name}, func() error {
		interests = append(interests, interest)
		return nil
	})
	if err != nil {
		return interests, errors.New(fmt.Sprintf("failed to append interests. Err: %s op:%s", err.Error(), op))
	}
	return interests, nil
}

func (r PostgresImpl) CreateUser(ctx context.Context, user models.User) (int64, error) {
	op := "repository.PostgresImpl.CreateUser"
	//insert new user into users
	var userID int64

	//start tx
	tx, err := r.pool.Begin(ctx)
	if err != nil {
		return -1, errors.New(fmt.Sprintf("failed to start tx. Err: %s op:%s", err.Error(), op))
	}
	defer tx.Rollback(ctx)
	query := `INSERT INTO users (name, email, hashed_password, refresh_token)  values ($1, $2, $3, $4) returning id`
	err = tx.QueryRow(ctx, query, user.Name, user.Email, user.HashedPassword, user.RefreshToken).Scan(&userID)
	if err != nil {
		return -1, errors.New(fmt.Sprintf("failed to create user. Err: %s op:%s", err.Error(), op))
	}
	//link interests
	//init batch to optimize query
	var batch pgx.Batch
	query = `INSERT INTO user_interests(user_id, interest_id) VALUES ($1, $2)`
	for _, interest := range user.Interests {
		batch.Queue(query, userID, interest)
	}

	//send batch
	br := tx.SendBatch(ctx, &batch)
	_, err = br.Exec()
	br.Close()
	if err != nil {
		return -1, errors.New(fmt.Sprintf("failed to send interests batch. Err: %s op:%s", err.Error(), op))
	}
	if err = tx.Commit(ctx); err != nil {
		return -1, errors.New(fmt.Sprintf("failed to commit changes. Err: %s op:%s", err.Error(), op))
	}
	return userID, nil
}

func (r PostgresImpl) GetUserByID(ctx context.Context, userID int64, fields []string) (models.User, error) {
	//TODO implement me
	panic("implement me")
}

func (r PostgresImpl) UpdateUserByID(ctx context.Context, userID int64, user models.User) bool {
	//TODO implement me
	panic("implement me")
}

func (r PostgresImpl) FindUserByEmail(ctx context.Context, email string) int64 {
	//TODO implement me
	panic("implement me")
}

func NewRepository(pool *pgxpool.Pool, log *slog.Logger) IRepository {
	return PostgresImpl{pool: pool, log: log}
}
