CREATE TABLE user_likes (
                            id SERIAL PRIMARY KEY,
                            user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            target_user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            liked BOOLEAN NOT NULL,
                            created_at TIMESTAMP DEFAULT now(),
                            UNIQUE (user_id, target_user_id)
);
