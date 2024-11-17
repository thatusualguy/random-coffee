package main

import (
	"google.golang.org/grpc"
	grpc2 "service.user/internal/handler/grpc"
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
	repo := repository.NewRepository(pool)
	gr := grpc.NewServer(grpc.EmptyServerOption{})
	srv := grpc2.NewServer(log, repo)
	err := grpc2.RegisterUserServiceServer(gr, *srv, cfg.Port)
	if err != nil {
		log.Error("failed to register user service", "err", err.Error())
		return
	}
}
