package ru.job4j.cars.repository;

import ru.job4j.cars.model.PostPhoto;

import java.util.Optional;

public interface PostPhotoRepository {
    PostPhoto save(PostPhoto postPhoto);

    Optional<PostPhoto> findById(int id);

    boolean delete(int id);
}
