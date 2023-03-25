CREATE TABLE engine
(
    id             SERIAL PRIMARY KEY,
    power          FLOAT   NOT NULL,
    cubic_capacity FLOAT   NOT NULL,
    FUEL           VARCHAR NOT NULL,
    UNIQUE (power, cubic_capacity, FUEL)
);
