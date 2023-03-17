ALTER TABLE engine
    DROP COLUMN name;
ALTER TABLE engine
    DROP COLUMN fuel;
ALTER TABLE engine
    ADD COLUMN fuel_id INT NOT NULL REFERENCES fuel (id);
