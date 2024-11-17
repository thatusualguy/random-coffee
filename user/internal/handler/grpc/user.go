package grpc

import (
	"context"
	"fmt"
	"github.com/qquiqlerr/randomCoffeeProtos/user"
	"google.golang.org/grpc"
	"log/slog"
	"net"
	"service.user/internal/models"
	"service.user/internal/repository"
	"service.user/utils"
)

type Server struct {
	user.UnimplementedUserServiceServer
	log  *slog.Logger
	repo repository.IRepository
}

func NewServer(log *slog.Logger, repo repository.IRepository) *Server {
	return &Server{
		log:  log,
		repo: repo,
	}
}

func (s *Server) CreateUser(context context.Context, request *user.CreateUserRequest) (*user.CreateUserResponse, error) {
	const op = "grpc.Server.CreateUser"
	log := s.log.With(
		slog.String("op", op),
	)
	var u models.User
	u = utils.RequestToUser(request)
	userID, err := s.repo.CreateUser(context, u)
	if err != nil {
		log.Error("failed to create user", slog.String("err", err.Error()))
		return nil, err
	}
	return &user.CreateUserResponse{UserId: userID}, nil
}

func (s *Server) GetUserByID(context context.Context, request *user.GetUserRequest) (*user.GetUserResponse, error) {
	const op = "grpc.Server.GetUserByID"
	log := s.log.With(
		slog.String("op", op),
	)
	fields := models.UserFields{}
	if request.Fields != nil {
		fields = models.UserFields{
			Name:           request.Fields.Name,
			Email:          request.Fields.Email,
			HashedPassword: request.Fields.HashedPassword,
			RefreshToken:   request.Fields.RefreshToken,
			Interests:      request.Fields.Interests,
			AvailableDates: request.Fields.AvailableDates,
			TgHandle:       request.Fields.TgHandle,
		}
	}
	u, err := s.repo.GetUserByID(context, request.GetUserId(), fields)
	if err != nil {
		log.Error("failed to get user", slog.String("err", err.Error()))
		return nil, err
	}
	interests := u.Interests
	if interests == nil {
		interests = []int64{}
	}

	availableDates := u.AvailableDates
	if availableDates == nil {
		availableDates = []int64{}
	}
	return &user.GetUserResponse{
		User: &user.User{
			Name:           u.Name,
			Email:          u.Email,
			HashedPassword: u.HashedPassword,
			RefreshToken:   u.RefreshToken,
			Interests:      interests,
			AvailableDates: availableDates,
			TgHandle:       u.TgHandle,
		},
	}, nil
}

func (s *Server) UpdateUserByID(context context.Context, request *user.UpdateUserRequest) (*user.UpdateUserResponse, error) {
	const op = "grpc.Server.UpdateUserByID"
	log := s.log.With(
		slog.String("op", op),
	)
	userID := request.UserId
	if request == nil {
		log.Error("request is nil")
		return nil, fmt.Errorf("%s: request is nil", op)
	}

	// Проверяем Fields
	fields := models.UserFields{}
	if request.Fields != nil {
		fields = models.UserFields{
			Name:           request.Fields.Name,
			Email:          request.Fields.Email,
			HashedPassword: request.Fields.HashedPassword,
			RefreshToken:   request.Fields.RefreshToken,
			Interests:      request.Fields.Interests,
			AvailableDates: request.Fields.AvailableDates,
			TgHandle:       request.Fields.TgHandle,
		}
	}
	var u models.User
	u.Name = request.Data.Name
	u.Email = request.Data.Email
	u.HashedPassword = request.Data.HashedPassword
	u.RefreshToken = request.Data.RefreshToken
	u.Interests = request.Data.Interests
	u.AvailableDates = request.Data.AvailableDates

	err := s.repo.UpdateUserByID(context, userID, fields, u)
	if err != nil {
		log.Error("failed to update user", slog.String("err", err.Error()))
		return &user.UpdateUserResponse{
			Status: false,
		}, err
	}
	return &user.UpdateUserResponse{
		Status: true,
	}, nil
}

func (s *Server) FindUserIDByEmail(context context.Context, request *user.FindUserIDRequest) (*user.FindUserIDResponse, error) {
	email := request.Email
	userID := s.repo.FindUserByEmail(context, email)
	return &user.FindUserIDResponse{
		UserId: userID,
	}, nil
}

func (s *Server) GetInterests(context context.Context, empty *user.Empty) (*user.GetInterestsResponse, error) {
	interests, err := s.repo.GetInterests(context)
	if err != nil {
		return nil, err
	}
	var interest []*user.Interest
	for _, i := range interests {
		interest = append(interest, &user.Interest{
			Id:       i.ID,
			Interest: i.Name,
		})
	}
	return &user.GetInterestsResponse{
		Interests: interest,
	}, nil
}

func RegisterUserServiceServer(s *grpc.Server, srv Server, port string) error {
	user.RegisterUserServiceServer(s, &srv)
	lis, err := net.Listen("tcp", ":"+port)
	if err != nil {
		return err
	}
	return s.Serve(lis)
}
