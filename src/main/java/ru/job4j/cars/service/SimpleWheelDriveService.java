package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.WheelDrive;

import java.util.Arrays;
import java.util.List;

@Service
public class SimpleWheelDriveService implements  WheelDriveService {
    @Override
    public List<WheelDrive> findAll() {
        return Arrays.asList(WheelDrive.values());
    }

}
