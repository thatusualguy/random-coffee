package main

import (
	"database/sql"
	"github.com/pressly/goose/v3"
	"service.user/internal/repository"
	"service.user/pkg/config"
	"service.user/pkg/logger"
	"service.user/pkg/storage"
)

func main() {
	cfg := config.MustLoad()
	pool := storage.MustConnect()
	log := logger.SetupLogger(cfg.Env)
	goose.Up(pool.(*sql.DB))
	repo := repository.NewRepository(pool, log)
}
