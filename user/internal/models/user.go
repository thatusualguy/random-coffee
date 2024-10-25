package models

type User struct {
	Name           string
	Email          string
	Interests      []string
	RefreshToken   string
	HashedPassword string
}
