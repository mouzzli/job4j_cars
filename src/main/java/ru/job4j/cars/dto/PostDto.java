package ru.job4j.cars.dto;

import lombok.Data;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private int id;
    private String description;
    private LocalDateTime created;
    private BigDecimal price;
    private Car car;
    private List<String> postPhotos;
    private boolean isActive;
    private User user;
}
