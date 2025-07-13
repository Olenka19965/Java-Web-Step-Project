CREATE TABLE users
(
    id        serial PRIMARY KEY,
    name      text not null,
    photo_url text not null
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    content TEXT NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE user_likes (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    target_user_id INT NOT NULL,
    liked BOOLEAN NOT NULL,
    UNIQUE (user_id, target_user_id)
);

CREATE TABLE auth (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
