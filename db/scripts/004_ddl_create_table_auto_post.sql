CREATE TABLE auto_post
(
    id           SERIAL PRIMARY KEY,
    description  VARCHAR              NOT NULL,
    created      TIMESTAMP            NOT NULL,
    auto_user_id INT REFERENCES auto_user (id),
    car_id       INT REFERENCES car (id),
    price        DECIMAL(10)          NOT NULL,
    is_active    BOOLEAN DEFAULT TRUE NOT NULL
);
