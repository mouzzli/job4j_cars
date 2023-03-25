package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Fuel;

import java.util.Arrays;
import java.util.List;

@Service
public class SimpleFuelService implements FuelService {

    @Override
    public List<Fuel> findAll() {
        return Arrays.asList(Fuel.values());
    }
}
