package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.Optional;

public interface EngineRepository {
    Engine save(Engine engine);

    Optional<Engine> findById(int id);

    void update(Engine engine);

    boolean delete(int id);

    Optional<Engine> exist(Engine engine);
}
