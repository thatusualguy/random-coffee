package models

type User struct {
	Name           string `db:"name"`
	Email          string `db:"email"`
	Interests      []int64
	AvailableDates []int64
	RefreshToken   string
	HashedPassword string `db:"hashed_password"`
}

type Interest struct {
	Name string
	ID   int64
}

type UserFields struct {
	Name           bool
	Email          bool
	Interests      bool
	RefreshToken   bool
	HashedPassword bool
	AvailableDates bool
}
