CREATE TABLE car
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR NOT NULL,
    engine_id INT     NOT NULL REFERENCES engine (id)
)