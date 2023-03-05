package ru.job4j.cars.repository;

import ru.job4j.cars.model.Driver;

import java.util.Optional;

public interface DriverRepository {
    Driver save(Driver driver);

    Optional<Driver> findById(int id);

    boolean update(Driver driver);

    boolean delete(int id);
}
