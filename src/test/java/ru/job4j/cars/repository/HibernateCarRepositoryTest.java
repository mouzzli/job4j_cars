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
import ru.job4j.cars.model.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarRepositoryTest {
    private static CarRepository carRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        carRepository = new HibernateCarRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();

    }

    @Test
    public void whenSaveThenFindById() {
        Engine engine = new Engine(0, 180, 2.4, Fuel.PETROL);
        Car car = new Car(0,
                "Mitsubishi",
                "Outlander XL",
                2010, engine,
                Transmission.CVT,
                WheelDrive.FULL_WD,
                Color.BLACK,
                Type.CROSSOVER,
                97000);
        Car savedCar = carRepository.save(car);
        assertThat(savedCar).isEqualTo(car);
        Optional<Car> optionalCar = carRepository.findById(savedCar.getId());
        assertThat(optionalCar.get()).isEqualTo(savedCar);
    }

    @Test
    public void whenSaveThenDelete() {
        Engine engine = new Engine(0, 180, 2.4, Fuel.PETROL);
        Car car = new Car(0,
                "Mitsubishi",
                "Outlander XL",
                2010, engine,
                Transmission.CVT,
                WheelDrive.FULL_WD,
                Color.BLACK,
                Type.CROSSOVER,
                97000);
        carRepository.save(car);
        assertThat(carRepository.delete(car.getId())).isTrue();
        assertThat(carRepository.delete(car.getId())).isFalse();
        assertThat(carRepository.findById(car.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        Engine engine = new Engine(0, 180, 2.4, Fuel.PETROL);
        Car car = new Car(0,
                "Mitsubishi",
                "Outlander XL",
                2010, engine,
                Transmission.CVT,
                WheelDrive.FULL_WD,
                Color.BLACK,
                Type.CROSSOVER,
                97000);
        carRepository.save(car);
        car.setBrand("Toyota");
        carRepository.update(car);
        assertThat(carRepository.findById(car.getId()).get()).isEqualTo(car);
    }
}