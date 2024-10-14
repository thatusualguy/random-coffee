package handlers

import (
	"RandomCoffee/auth/internal/config"
	"RandomCoffee/auth/internal/jwt"
	"context"
	"errors"
	"fmt"
	"github.com/Qquiqlerr/randomCoffeeProtos/auth"
	"github.com/Qquiqlerr/randomCoffeeProtos/user"
	"golang.org/x/crypto/bcrypt"
)

type AuthService struct {
	UserService user.UserServiceClient
	cfg         *config.Config
	auth.UnimplementedAuthServiceServer
}

func NewAuthService(client user.UserServiceClient, cfg *config.Config) *AuthService {
	return &AuthService{
		UserService: client,
		cfg:         cfg,
	}
}

func (a *AuthService) Register(ctx context.Context, req *auth.RegisterRequest) (*auth.RegisterResponse, error) {
	if err := ValidateRequest(req); err != nil {
		return nil, err
	}
	passHash, err := jwt.HashToken(req.Password)
	if err != nil {
		return nil, err
	}
	refreshToken, err := jwt.NewRefreshToken()
	resp, err := a.UserService.CreateUser(context.Background(), &user.CreateUserRequest{
		User: &user.User{
			Name:           req.Name,
			Email:          req.Email,
			Interests:      req.Interests,
			HashedPassword: passHash,
			RefreshToken:   refreshToken,
		},
	})
	if err != nil {
		return nil, err
	}
	accessToken, err := jwt.NewAccessToken(resp.UserId, a.cfg.AccessTokenTTL, a.cfg.Secret)
	if err != nil {
		return nil, err
	}

	if err != nil {
		return nil, err
	}
	if err != nil {
		return nil, err
	}
	return &auth.RegisterResponse{UserId: resp.UserId, Token: accessToken, RefreshToken: refreshToken}, nil
}

func (a *AuthService) Login(ctx context.Context, req *auth.LoginRequest) (*auth.LoginResponse, error) {
	resp, err := a.UserService.FindUserIDByEmail(context.Background(), &user.FindUserIDRequest{
		Email: req.Email,
	})
	if err != nil {
		return nil, err
	}
	hash, err := a.UserService.GetUserByID(context.Background(), &user.GetUserRequest{
		UserId: resp.UserId,
		Fields: []string{"hashed_password"},
	})
	if err != nil {
		return nil, err
	}
	if err = bcrypt.CompareHashAndPassword([]byte(hash.User.HashedPassword), []byte(req.Password)); err != nil {
		return nil, err
	}
	accessToken, err := jwt.NewAccessToken(resp.UserId, a.cfg.AccessTokenTTL, a.cfg.Secret)
	if err != nil {
		return nil, err
	}
	refreshToken, err := jwt.NewRefreshToken()
	if err != nil {
		return nil, err
	}
	return &auth.LoginResponse{
		Token:        accessToken,
		RefreshToken: refreshToken,
	}, nil
}
func (a *AuthService) RefreshToken(ctx context.Context, req *auth.RefreshTokenRequest) (*auth.RefreshTokenResponse, error) {
	resp, err := a.UserService.GetUserByID(context.Background(), &user.GetUserRequest{
		UserId: req.UserId,
		Fields: []string{"refresh_token"},
	})
	if err != nil {
		return nil, err
	}
	if resp.User.RefreshToken != req.RefreshToken {
		return nil, fmt.Errorf("invalid refresh token")
	}
	accessToken, err := jwt.NewAccessToken(req.UserId, a.cfg.AccessTokenTTL, a.cfg.Secret)
	if err != nil {
		return nil, err
	}
	refreshToken, err := jwt.NewRefreshToken()
	if err != nil {
		return nil, err
	}
	status, err := a.UserService.UpdateUserByID(context.Background(), &user.UpdateUserRequest{
		UserId: req.UserId,
		Fields: &user.User{
			RefreshToken: refreshToken,
		},
	})
	if err != nil || !status.Status {
		return nil, err
	}
	return &auth.RefreshTokenResponse{
		AccessToken:  accessToken,
		RefreshToken: refreshToken,
	}, nil
}

func ValidateRequest(req *auth.RegisterRequest) error {
	if req.Name == "" {
		return errors.New("name cannot be empty")
	}
	if req.Email == "" {
		return errors.New("email cannot be empty")
	}
	if len(req.Interests) == 0 {
		return errors.New("interests cannot be empty")
	}
	return nil
}
