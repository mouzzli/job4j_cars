package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Fuel;
import ru.job4j.cars.repository.FuelRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleFuelService implements  FuelService {
    private FuelRepository fuelRepository;

    @Override
    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }
}
