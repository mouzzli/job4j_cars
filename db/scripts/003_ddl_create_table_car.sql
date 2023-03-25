CREATE TABLE car
(
    id                SERIAL PRIMARY KEY,
    brand             VARCHAR                    NOT NULL,
    model             VARCHAR                    NOT NULL,
    manufactured_year INT                        NOT NULL,
    mileage           INT                        NOT NULL,
    color             VARCHAR                    NOT NULL,
    transmission      VARCHAR                    NOT NULL,
    type              VARCHAR                    NOT NULL,
    wheel_drive       VARCHAR                    NOT NULL,
    engine_id         INT REFERENCES engine (id)
)