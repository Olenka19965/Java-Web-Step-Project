CREATE TABLE user_likes (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
target_user_id INT NOT NULL,
liked BOOLEAN NOT NULL,
UNIQUE (user_id, target_user_id)
);