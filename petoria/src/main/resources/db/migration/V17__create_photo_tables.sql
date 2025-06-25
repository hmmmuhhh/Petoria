CREATE TABLE pet_photos (
    pet_id BIGINT NOT NULL REFERENCES listed_pets(id) ON DELETE CASCADE,
    photo_path TEXT
);

CREATE TABLE notice_photos (
    notice_id BIGINT NOT NULL REFERENCES lost_and_found_notice(id) ON DELETE CASCADE,
    photo_path TEXT
);