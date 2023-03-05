ALTER TABLE car
    ADD COLUMN transmission_id INT NOT NULL REFERENCES transmission (id);
ALTER TABLE car
    ADD COLUMN manufactured_year INT NOT NULL;
ALTER TABLE car
    ADD COLUMN mileage INT NOT NULL;
