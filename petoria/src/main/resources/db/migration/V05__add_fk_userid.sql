ALTER TABLE users
DROP COLUMN id;

ALTER TABLE users
ADD COLUMN id BIGSERIAL PRIMARY KEY;

ALTER TABLE listed_pets
ADD CONSTRAINT fk_user_id
FOREIGN KEY (user_id)
REFERENCES users(id)
ON DELETE SET NULL;