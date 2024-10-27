package models

type User struct {
	Name           string `db:"name"`
	Email          string `db:"email"`
	Interests      []int64
	RefreshToken   string
	HashedPassword string `db:"hashed_password"`
}

type Interest struct {
	name string
	id   int64
}
