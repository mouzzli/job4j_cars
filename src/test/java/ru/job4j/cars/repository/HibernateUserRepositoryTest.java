package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateUserRepositoryTest {
    private static UserRepository userRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        userRepository = new HibernateUserRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindByLoginAndPassword() {
        String login = "login";
        String password = "password";
        User user = new User(0, login, password);
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isEqualTo(user);
        Optional<User> optionalUser = userRepository.findByLoginAndPassword(login, password);
        assertThat(optionalUser.get()).isEqualTo(user);
    }
}