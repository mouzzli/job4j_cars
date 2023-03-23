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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernatePostRepositoryTest {
    private static PostRepository postRepository;
    private static ColorRepository colorRepository;
    private static EngineRepository engineRepository;
    private static WheelDriveRepository wheelDriveRepository;
    private static TypeRepository typeRepository;
    private static TransmissionRepository transmissionRepository;
    private static CarRepository carRepository;
    private static UserRepository userRepository;
    private static PostPhotoRepository postPhotoRepository;
    private static FuelRepository fuelRepository;
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
        postRepository = new HibernatePostRepository(crudRepository);
        userRepository = new HibernateUserRepository(crudRepository);
        postPhotoRepository = new HibernatePostPhotoRepository(crudRepository);
        fuelRepository = new HibernateFuelRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM PostPhoto").executeUpdate();
        session.createQuery("DELETE FROM Post ").executeUpdate();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM Transmission").executeUpdate();
        session.createQuery("DELETE FROM Type").executeUpdate();
        session.createQuery("DELETE FROM WheelDrive").executeUpdate();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        session.createQuery("DELETE FROM Color").executeUpdate();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.createQuery("DELETE FROM Fuel ").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        carRepository.save(car);
        Post post = new Post(0, "testDescription", LocalDateTime.of(2022, 2, 1, 12, 50), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        postRepository.save(post);
        assertThat(postRepository.findById(post.getId()).get()).isEqualTo(post);
    }

    @Test
    public void whenSaveThenFindByBrand() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        Car car2 = new Car(0, "Toyota", "Corolla", 2008, engine, transmission, wheelDrive, color, type, 110000);
        carRepository.save(car);
        carRepository.save(car2);
        Post post = new Post(0, "testDescription", LocalDateTime.of(2022, 2, 1, 12, 50), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        Post post2 = new Post(0, "testDescription", LocalDateTime.of(2021, 1, 1, 12, 50), BigDecimal.valueOf(1000000), user, car2, new ArrayList<>(), true);
        postRepository.save(post);
        postRepository.save(post2);
        assertThat(postRepository.findByBrand(post.getCar().getBrand())).isEqualTo(List.of(post));
    }

    @Test
    public void whenSaveThenFindByLastDay() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        Car car2 = new Car(0, "Mitsubishi", "Outlander XL", 2008, engine, transmission, wheelDrive, color, type, 110000);
        carRepository.save(car);
        carRepository.save(car2);
        Post post = new Post(0, "testDescription", LocalDateTime.now().minus(1, ChronoUnit.HOURS), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        Post post2 = new Post(0, "testDescription", LocalDateTime.now().minus(3, ChronoUnit.DAYS), BigDecimal.valueOf(1000000), user, car2, new ArrayList<>(), true);
        postRepository.save(post);
        postRepository.save(post2);
        assertThat(postRepository.findByLastDay()).isEqualTo(List.of(post));
    }

    @Test
    public void whenSaveThenDelete() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        carRepository.save(car);
        Post post = new Post(0, "testDescription", LocalDateTime.now().minus(1, ChronoUnit.HOURS), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        postRepository.save(post);
        assertThat(postRepository.delete(post.getId())).isTrue();
        assertThat(postRepository.delete(post.getId())).isFalse();
        assertThat(postRepository.findById(post.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        carRepository.save(car);
        Post post = new Post(0, "testDescription", LocalDateTime.now().minus(1, ChronoUnit.HOURS), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        postRepository.save(post);
        post.setDescription("new Description");
        assertThat(postRepository.update(post)).isTrue();
        assertThat(postRepository.findById(post.getId()).get().getDescription()).isEqualTo("new Description");
        post.setId(0);
        assertThat(postRepository.update(post)).isFalse();
    }

    @Test
    public void whenSaveThenFindByPhoto() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        Car car2 = new Car(0, "Toyota", "Corolla", 2008, engine, transmission, wheelDrive, color, type, 110000);
        carRepository.save(car);
        carRepository.save(car2);
        Post post = new Post(0, "testDescription", LocalDateTime.of(2022, 2, 1, 12, 50), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        Post post2 = new Post(0, "testDescription", LocalDateTime.of(2021, 1, 1, 12, 50), BigDecimal.valueOf(1000000), user, car2, new ArrayList<>(), true);
        postRepository.save(post);
        postRepository.save(post2);
        PostPhoto postPhoto = new PostPhoto(0, "test photo", new byte[]{1, 2}, post.getId());
        postPhotoRepository.save(postPhoto);
        assertThat(postRepository.findWithPhoto()).isEqualTo(List.of(post));
    }

    @Test
    public void whenSaveThenFinAll() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        Car car2 = new Car(0, "Toyota", "Corolla", 2008, engine, transmission, wheelDrive, color, type, 110000);
        carRepository.save(car);
        carRepository.save(car2);
        Post post = new Post(0, "testDescription", LocalDateTime.of(2022, 2, 1, 12, 50), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        Post post2 = new Post(0, "testDescription", LocalDateTime.of(2021, 1, 1, 12, 50), BigDecimal.valueOf(1000000), user, car2, new ArrayList<>(), true);
        postRepository.save(post);
        postRepository.save(post2);
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSameElementsAs(List.of(post, post2));
    }

    @Test
    public void whenChangeStatus() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        Engine engine = new Engine(0, 180, 2.4, fuel);
        engineRepository.save(engine);
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        Type type = new Type(0, "Внедорожник");
        typeRepository.save(type);
        Transmission transmission = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, transmission, wheelDrive, color, type, 97000);
        carRepository.save(car);
        Post post = new Post(0, "testDescription", LocalDateTime.now().minus(1, ChronoUnit.HOURS), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        postRepository.save(post);
        assertThat(postRepository.changeStatus(false, post.getId())).isTrue();
        Optional<Post> savedPost = postRepository.findById(post.getId());
        assertThat(savedPost.get().isActive()).isEqualTo(false);
    }
}