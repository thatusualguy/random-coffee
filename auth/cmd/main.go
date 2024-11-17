package main

import (
	"github.com/joho/godotenv"
	"github.com/qquiqlerr/randomCoffeeProtos/auth"
	"github.com/qquiqlerr/randomCoffeeProtos/user"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"log"
	"net"
	"os"
	"path/filepath"
	"service.auth/internal/config"
	"service.auth/internal/handlers"
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
	absPath, _ := filepath.Abs(".env")
	err := godotenv.Load(absPath)
	if err != nil {
		panic("can`t load service paths")
	}
	userServiceHost := os.Getenv("USER_SERVICE_HOST")
	userServicePort := os.Getenv("USER_SERVICE_PORT")
	opts := []grpc.DialOption{grpc.WithTransportCredentials(insecure.NewCredentials())}
	conn, err := grpc.NewClient(userServiceHost+":"+userServicePort, opts...)
	if err != nil {
		panic(err.Error())
	}
	return user.NewUserServiceClient(conn)
}
