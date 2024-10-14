package main

import (
	"RandomCoffee/api/proto/auth"
	"RandomCoffee/api/proto/user"
	"RandomCoffee/auth/internal/config"
	"RandomCoffee/auth/internal/handlers"
	"github.com/joho/godotenv"
	"google.golang.org/grpc"
	"log"
	"net"
	"os"
	"path/filepath"
)

func main() {
	cfg := config.MustLoadConfig()

	lis, err := net.Listen("tcp", cfg.Port)
	if err != nil {
		panic(err)
	}
	defer lis.Close()

	s := grpc.NewServer(grpc.ConnectionTimeout(cfg.Timeout))
	UserService := MustConnectToUserService()
	auth.RegisterAuthServiceServer(s, handlers.NewAuthService(UserService, cfg))
	log.Println("Starting server on port " + cfg.Port)
	if err := s.Serve(lis); err != nil {
		panic(err)
	}
}

func MustConnectToUserService() user.UserServiceClient {
	absPath, _ := filepath.Abs("../.env.paths")
	err := godotenv.Load(absPath)
	if err != nil {
		panic("can`t load service paths")
	}
	userServiceHost := os.Getenv("USER_SERVICE_HOST")
	userServicePort := os.Getenv("USER_SERVICE_PORT")
	conn, err := grpc.NewClient(userServiceHost + ":" + userServicePort)
	if err != nil {
		panic("failed to connect to UserService")
	}
	return user.NewUserServiceClient(conn)
}
