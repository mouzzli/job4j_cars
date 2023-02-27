ALTER TABLE auto_post
    ADD car_id INT NOT NULL UNIQUE REFERENCES car (id);
