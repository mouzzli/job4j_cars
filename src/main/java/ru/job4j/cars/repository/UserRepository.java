package ru.job4j.cars.repository;

import ru.job4j.cars.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);
}
