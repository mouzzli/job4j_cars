package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Color;

import java.util.Arrays;
import java.util.List;

@Service
public class SimpleColorService implements ColorService {

    @Override
    public List<Color> findAll() {
        return Arrays.asList(Color.values());
    }
}
