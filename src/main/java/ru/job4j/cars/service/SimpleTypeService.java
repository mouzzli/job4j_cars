package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Type;

import java.util.Arrays;
import java.util.List;

@Service
public class SimpleTypeService implements TypeService {
    @Override
    public List<Type> findAll() {
        return Arrays.asList(Type.values());
    }
}
