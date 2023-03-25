CREATE TABLE post_photo
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR,
    file    bytea,
    post_id INT REFERENCES auto_post (id) NOT NULL
)