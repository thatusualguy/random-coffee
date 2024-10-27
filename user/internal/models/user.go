package models

type User struct {
	Name           string   `db:"name"`
	Email          string   `db:"email"`
	Interests      []string `db:"hashed_password"`
	RefreshToken   string
	HashedPassword string
}
