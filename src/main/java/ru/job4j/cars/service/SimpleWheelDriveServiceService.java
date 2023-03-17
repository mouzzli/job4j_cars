package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.WheelDrive;
import ru.job4j.cars.repository.WheelDriveRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleWheelDriveServiceService implements WheelDriveService {
    private WheelDriveRepository wheelDriveRepository;

    @Override
    public List<WheelDrive> findAll() {
        return wheelDriveRepository.findAll();
    }
}
