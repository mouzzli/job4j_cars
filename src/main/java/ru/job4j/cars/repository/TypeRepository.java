package ru.job4j.cars.repository;

import ru.job4j.cars.model.Type;

import java.util.List;
import java.util.Optional;

public interface TypeRepository {
    Type save(Type type);

    Optional<Type> findById(int id);

    boolean update(Type type);

    boolean delete(int id);

    List<Type> findAll();
}
