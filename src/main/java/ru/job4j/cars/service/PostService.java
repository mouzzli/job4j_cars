package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.io.IOException;
import java.util.List;

public interface PostService {

    Post save(Post post, Car car, Engine engine, User user, List<MultipartFile> photos) throws IOException;

    boolean delete(int id);
}
