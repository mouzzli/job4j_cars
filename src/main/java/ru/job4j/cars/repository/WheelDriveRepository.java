package ru.job4j.cars.repository;

import ru.job4j.cars.model.WheelDrive;

import java.util.List;
import java.util.Optional;

public interface WheelDriveRepository {
    WheelDrive save(WheelDrive wheelDrive);

    Optional<WheelDrive> findById(int id);

    boolean update(WheelDrive wheelDrive);

    boolean delete(int id);

    List<WheelDrive> findAll();
}
