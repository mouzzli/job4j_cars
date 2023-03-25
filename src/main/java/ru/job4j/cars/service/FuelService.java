package ru.job4j.cars.service;

import ru.job4j.cars.model.Fuel;

import java.util.List;

public interface FuelService {
    List<Fuel> findAll();
}
