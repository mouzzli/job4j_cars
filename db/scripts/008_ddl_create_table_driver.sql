CREATE TABLE driver
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR,
    user_id INT NOT NULL UNIQUE REFERENCES auto_user (id)
)