package repository

import (
	"context"
	"github.com/jackc/pgx/v5/pgxpool"
	"log/slog"
	"service.user/internal/models"
)

type IRepository interface {
	CreateUser(ctx context.Context, user models.User) (int64, error)
	GetUserByID(ctx context.Context, userID int64, fields []string) (models.User, error)
	UpdateUserByID(ctx context.Context, userID int64, user models.User) bool
	FindUserByEmail(ctx context.Context, email string) int64
}

type PostgresImpl struct {
	log  *slog.Logger
	pool *pgxpool.Pool
}

func (r PostgresImpl) CreateUser(ctx context.Context, user models.User) (int64, error) {
	_, err := r.pool.Exec(ctx, `INSERT INTO users values `)
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
