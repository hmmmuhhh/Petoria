CREATE TABLE lost_and_found_notice (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    photo_url TEXT,
    location VARCHAR(255),
    type VARCHAR(10) CHECK (type IN ('LOST', 'FOUND')) NOT NULL,
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);
