package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    boolean update(Post post);

    boolean delete(int id);

    Optional<Post> findById(int id);

    List<Post> findByLastDay();

    List<Post> findWithPhoto();

    List<Post> findByBrand(String brand);

    List<Post> findAll();
}