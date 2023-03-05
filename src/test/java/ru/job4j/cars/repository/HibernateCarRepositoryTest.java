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
    private static ColorRepository colorRepository;
    private static EngineRepository engineRepository;
    private static WheelDriveRepository wheelDriveRepository;
    private static TypeRepository typeRepository;
    private static TransmissionRepository transmissionRepository;
    private static CarRepository carRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        colorRepository = new HibernateColorRepository(crudRepository);
        engineRepository = new HibernateEngineRepository(crudRepository);
        wheelDriveRepository = new HibernateWheelDriveRepository(crudRepository);
        typeRepository = new HibernateTypeRepository(crudRepository);
        transmissionRepository = new HibernateTransmissionRepository(crudRepository);
        carRepository = new HibernateCarRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM Transmission").executeUpdate();
        session.createQuery("DELETE FROM Type").executeUpdate();
        session.createQuery("DELETE FROM WheelDrive").executeUpdate();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        session.createQuery("DELETE FROM Color").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();

    }

    @Test
    public void whenSaveThenFindById() {
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Engine engine = new Engine(0, "4B12", 180, 2.4, "безниновый");
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0,
                "Mitsubishi",
                "Outlander XL",
                2010, engine,
                transmission,
                wheelDrive,
                color,
                type,
                "97000");

        Car savedCar = carRepository.save(car);
        assertThat(savedCar).isEqualTo(car);
        Optional<Car> optionalCar = carRepository.findById(savedCar.getId());
        assertThat(optionalCar.get()).isEqualTo(savedCar);
    }

    @Test
    public void whenSaveThenDelete() {
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Engine engine = new Engine(0, "4B12", 180, 2.4, "безниновый");
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0,
                "Mitsubishi",
                "Outlander XL",
                2010, engine,
                transmission,
                wheelDrive,
                color,
                type,
                "97000");
        carRepository.save(car);
        assertThat(carRepository.delete(car.getId())).isTrue();
        assertThat(carRepository.delete(car.getId())).isFalse();
        assertThat(carRepository.findById(car.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Engine engine = new Engine(0, "4B12", 180, 2.4, "безниновый");
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0,
                "Mitsubishi",
                "Outlander XL",
                2010, engine,
                transmission,
                wheelDrive,
                color,
                type,
                "97000");
        carRepository.save(car);
        car.setBrand("Toyota");
        assertThat(carRepository.update(car)).isTrue();
        assertThat(carRepository.findById(car.getId()).get()).isEqualTo(car);
        car.setId(0);
        assertThat(carRepository.update(car)).isFalse();
    }
}