CREATE TABLE post_photo
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR,
    file    bytea,
    post_id INT NOT NULL REFERENCES auto_post (id)
)