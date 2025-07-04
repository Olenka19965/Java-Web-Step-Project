create table users
(
    id        serial PRIMARY KEY,
    name      text not null,
    photo_url text not null
);

alter table users
    owner to "user";
INSERT INTO users (id, name, photo_url) VALUES
('1', 'Anna Novak', 'https://randomuser.me/api/portraits/women/1.jpg'),
('2','Mike Black', 'https://randomuser.me/api/portraits/men/2.jpg'),
('3','Sophie Blue', 'https://randomuser.me/api/portraits/women/3.jpg'),
('4','John White', 'https://randomuser.me/api/portraits/men/4.jpg');

