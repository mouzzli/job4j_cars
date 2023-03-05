package ru.job4j.cars.repository;

import ru.job4j.cars.model.Transmission;

import java.util.Optional;

public interface TransmissionRepository {

    Transmission save(Transmission transmission);

    Optional<Transmission> findById(int id);

    boolean delete(int id);

    boolean update(Transmission transmission);
}
