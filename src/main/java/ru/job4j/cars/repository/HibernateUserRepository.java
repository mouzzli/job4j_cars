package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private static final String FIND_BY_LOGIN_PASSWORD = "FROM User "
            + "WHERE login = :login AND password = :password";
    private CrudRepository crudRepository;

    @Override
    public User save(User user) {
        crudRepository.run(session -> session.persist(user));
        return user;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(FIND_BY_LOGIN_PASSWORD, User.class, Map.of(
                "login", login,
                "password", password
        ));
    }
}
