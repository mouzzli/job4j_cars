package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;

public interface PostService {

    Post save(Post post, Car car, Engine engine);

    boolean delete(int id);
}
