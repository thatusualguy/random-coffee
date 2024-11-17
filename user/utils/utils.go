package utils

import (
	"github.com/qquiqlerr/randomCoffeeProtos/user"
	"service.user/internal/models"
)

func RequestToUser(request *user.CreateUserRequest) models.User {
	return models.User{
		Name:           request.User.Name,
		Email:          request.User.Email,
		HashedPassword: request.User.HashedPassword,
		RefreshToken:   request.User.RefreshToken,
		Interests:      request.User.Interests,
		AvailableDates: request.User.AvailableDates,
		TgHandle:       request.User.TgHandle,
	}
}
