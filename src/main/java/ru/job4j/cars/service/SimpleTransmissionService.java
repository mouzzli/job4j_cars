package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Transmission;

import java.util.Arrays;
import java.util.List;

@Service
public class SimpleTransmissionService implements  TransmissionService {

    @Override
    public List<Transmission> findAll() {
        return Arrays.asList(Transmission.values());
    }
}
