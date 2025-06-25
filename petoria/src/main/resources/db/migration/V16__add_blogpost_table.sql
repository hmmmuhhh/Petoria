CREATE TABLE blog_post (
    id SERIAL PRIMARY KEY,
    content TEXT,
    created_at TIMESTAMP NOT NULL,
    creator_id INTEGER NOT NULL REFERENCES users(id)
);

CREATE TABLE blog_post_images (
    post_id INTEGER NOT NULL REFERENCES blog_post(id),
    image_path TEXT
);

CREATE TABLE blog_post_videos (
    post_id INTEGER NOT NULL REFERENCES blog_post(id),
    video_path TEXT
);
