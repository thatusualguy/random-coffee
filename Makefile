auth_proto:
	protoc --go_out=. --go-grpc_out=. ./api/proto/auth/auth.proto

user_proto:
	protoc --go_out=. --go-grpc_out=. ./api/proto/user/user.proto