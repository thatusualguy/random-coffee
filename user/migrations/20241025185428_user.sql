-- +goose Up
-- +goose StatementBegin
CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      hashed_password VARCHAR(255) not null,
                      refresh_token VARCHAR(255),
                      tg_handle VARCHAR(255),
                      created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE interests (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL UNIQUE,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE user_interests(
                               user_id integer REFERENCES users(id) ON DELETE CASCADE,
                               interest_id integer REFERENCES interests(id) ON DELETE CASCADE,
                               primary key (user_id, interest_id)
);
CREATE TABLE available_dates (
                                 user_id INT REFERENCES users(id),
                                 available_date timestamp
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_user_interests_user_id ON user_interests(user_id);
CREATE INDEX idx_user_interests_interest_id ON user_interests(interest_id);
CREATE INDEX idx_available_dates_user_id ON available_dates(user_id);
-- +goose StatementEnd

-- +goose Down
-- +goose StatementBegin
-- DROP INDEX IF EXISTS idx_users_email;
-- DROP INDEX IF EXISTS idx_user_interests_user_id;
-- DROP INDEX IF EXISTS idx_user_interests_interest_id;
-- DROP INDEX IF EXISTS idx_available_dates_user_id;
-- DROP TABLE IF EXISTS available_dates;
-- DROP TABLE IF EXISTS user_interests;
-- DROP TABLE IF EXISTS interests;
-- DROP TABLE IF EXISTS users;
-- +goose StatementEnd
