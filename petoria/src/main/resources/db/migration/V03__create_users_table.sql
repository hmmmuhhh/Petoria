CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    birthday DATE
);

ALTER TABLE listed_pets
DROP COLUMN provider_id;

ALTER TABLE listed_pets
ADD COLUMN user_id UUID;

ALTER TABLE listed_pets
ADD CONSTRAINT fk_user
FOREIGN KEY (user_id) REFERENCES users(id)
ON DELETE SET NULL;