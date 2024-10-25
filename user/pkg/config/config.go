package config

import (
	"github.com/joho/godotenv"
	"log"
	"os"
	"time"
)

type Config struct {
	Port    string        `env:"GRPC_PORT" env-default:"9090"`
	Timeout time.Duration `env:"GRPC_TIMEOUT" env-default:"10s"`
	Env     string        `env:"ENV" env-default:"prod"`
}

func MustLoad() *Config {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("failed to load .env", "err", err.Error())
	}
	port := os.Getenv("GRPC_PORT")
	timeout := os.Getenv("GRPC_TIMEOUT")
	env := os.Getenv("ENV")

	//set defaults
	if port == "" {
		port = "9090"
	}
	if timeout == "" {
		timeout = "10s"
	}
	if env == "" {
		env = "prod"
	}
	if env != "local" && env != "prod" {
		log.Print("invalid env, set default")
		timeout = "prod"
	}
	duration, err := time.ParseDuration(timeout)
	if err != nil {
		log.Print("invalid duration, set default")
		duration = time.Duration(10 * time.Second)
	}
	return &Config{
		Port:    port,
		Timeout: duration,
		Env:     env,
	}
}
