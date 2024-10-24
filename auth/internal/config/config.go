package config

import (
	"github.com/ilyakaznacheev/cleanenv"
	"github.com/joho/godotenv"
	"log"
	"os"
	"time"
)

type Config struct {
	AccessTokenTTL  time.Duration `yaml:"access_token_ttl"`
	RefreshTokenTTL time.Duration `yaml:"refresh_token_ttl"`
	GRPC            `yaml:"grpc"`
	Secret          string
}

type GRPC struct {
	Port    string        `yaml:"port"`
	Timeout time.Duration `yaml:"timeout"`
}

func MustLoadConfig() *Config {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env file")
	}
	path := os.Getenv("CONFIG_PATH")
	if path == "" {
		panic("empty config path")
	}
	if _, err := os.Stat(path); os.IsNotExist(err) {
		panic("config is not exist")
	}
	var cfg Config
	if err := cleanenv.ReadConfig(path, &cfg); err != nil {
		panic("failed to parse config")
	}
	cfg.Secret = os.Getenv("SECRET")
	return &cfg
}
