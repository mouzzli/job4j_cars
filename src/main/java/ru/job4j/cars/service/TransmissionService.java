package ru.job4j.cars.service;

import ru.job4j.cars.model.Transmission;

import java.util.List;

public interface TransmissionService {
    List<Transmission> findAll();
}

