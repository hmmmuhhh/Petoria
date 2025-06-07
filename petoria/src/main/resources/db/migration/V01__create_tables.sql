CREATE TABLE official_providers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) CHECK (type IN ('SHOP', 'SHELTER')) NOT NULL,
    phone VARCHAR(30),
    location VARCHAR(150),
    website_url TEXT,
    logo_url TEXT
);

CREATE TABLE listed_pets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2),
    description TEXT,
    submission_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    photo_url TEXT,
    provider_id INT REFERENCES official_providers(id) ON DELETE SET NULL
);