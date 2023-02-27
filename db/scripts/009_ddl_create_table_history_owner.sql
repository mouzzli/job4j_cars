CREATE TABLE history_owner
(
    id        SERIAL PRIMARY KEY,
    car_id    INT NOT NULL REFERENCES car (id),
    driver_id INT NOT NULL REFERENCES driver (id),
    startAt   TIMESTAMP WITHOUT TIME ZONE,
    endAt     TIMESTAMP WITHOUT TIME ZONE
);
