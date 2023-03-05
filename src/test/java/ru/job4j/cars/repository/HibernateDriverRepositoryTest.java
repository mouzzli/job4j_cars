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
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateDriverRepositoryTest {

    private static DriverRepository driverRepository;
    private static UserRepository userRepository;

    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        driverRepository = new HibernateDriverRepository(crudRepository);
        userRepository = new HibernateUserRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Driver").executeUpdate();
        session.createQuery("DELETE FROM User").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Driver driver = new Driver(0, "testDriver", user);
        Driver savedDriver = driverRepository.save(driver);
        assertThat(savedDriver).isEqualTo(driver);
        Optional<Driver> optionalDriver = driverRepository.findById(savedDriver.getId());
        assertThat(optionalDriver.get()).isEqualTo(savedDriver);
    }

    @Test
    public void whenSaveThenDelete() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Driver driver = new Driver(0, "testDriver", user);
        driverRepository.save(driver);
        assertThat(driverRepository.delete(driver.getId())).isTrue();
        assertThat(driverRepository.delete(driver.getId())).isFalse();
        assertThat(driverRepository.findById(driver.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Driver driver = new Driver(0, "testDriver", user);
        driverRepository.save(driver);
        driver.setName("test");
        assertThat(driverRepository.update(driver)).isTrue();
        assertThat(driverRepository.findById(driver.getId()).get()).isEqualTo(driver);
        driver.setId(0);
        assertThat(driverRepository.update(driver)).isFalse();
    }
}