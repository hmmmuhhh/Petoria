CREATE TABLE comment (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    image_url TEXT,
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    notice_id BIGINT REFERENCES lost_and_found_notice(id) ON DELETE CASCADE,
    blog_id BIGINT
);
