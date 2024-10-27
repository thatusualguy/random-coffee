package main

import (
	"service.user/internal/repository"
	"service.user/pkg/config"
	"service.user/pkg/logger"
	"service.user/pkg/storage"
)

func main() {
	cfg := config.MustLoad()
	pool := storage.MustConnect()
	defer pool.Close()
	log := logger.SetupLogger(cfg.Env)
	repo := repository.NewRepository(pool, log)
}
