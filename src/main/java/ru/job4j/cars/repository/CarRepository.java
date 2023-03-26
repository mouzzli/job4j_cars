package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.Optional;

public interface CarRepository {
    Car save(Car car);

    Optional<Car> findById(int id);

    void update(Car car);

    boolean delete(int id);

}
