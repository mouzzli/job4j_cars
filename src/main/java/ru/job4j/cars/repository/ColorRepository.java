package ru.job4j.cars.repository;

import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Optional;

public interface ColorRepository {
    Color save(Color color);

    Optional<Color> findById(int id);

    List<Color> findAll();

    boolean update(Color color);

    boolean delete(int id);
}
