ALTER TABLE car
    ADD COLUMN wheel_drive_id INT REFERENCES wheel_drive (id);
