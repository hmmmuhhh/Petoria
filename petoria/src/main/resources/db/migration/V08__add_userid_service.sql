ALTER TABLE service_providers
ADD COLUMN user_id BIGINT,
ADD CONSTRAINT fk_service_user FOREIGN KEY (user_id)
REFERENCES users(id) ON DELETE SET NULL;