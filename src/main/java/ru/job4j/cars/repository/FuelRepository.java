package ru.job4j.cars.repository;

import ru.job4j.cars.model.Fuel;

import java.util.List;
import java.util.Optional;

public interface FuelRepository {
    Fuel save(Fuel fuel);

    Optional<Fuel> findById(int id);

    boolean update(Fuel fuel);

    boolean delete(int id);

    List<Fuel> findAll();
}
