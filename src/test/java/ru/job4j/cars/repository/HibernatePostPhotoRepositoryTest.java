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
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class HibernatePostPhotoRepositoryTest {
    private static PostRepository postRepository;
    private static UserRepository userRepository;
    private static PostPhotoRepository postPhotoRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        postRepository = new HibernatePostRepository(crudRepository);
        userRepository = new HibernateUserRepository(crudRepository);
        postPhotoRepository = new HibernatePostPhotoRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM PostPhoto").executeUpdate();
        session.createQuery("DELETE FROM Post ").executeUpdate();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        session.createQuery("DELETE FROM User").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Engine engine = new Engine(0, 180, 2.4, Fuel.PETROL);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, Transmission.CVT, WheelDrive.FULL_WD, Color.BLACK, Type.CROSSOVER, 97000);
        Post post = new Post(0, "testDescription", LocalDateTime.of(2022, 2, 1, 12, 50), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        postRepository.save(post);
        PostPhoto postPhoto = new PostPhoto(0, "new Photo", new byte[]{1, 2}, post.getId());
        postPhotoRepository.save(postPhoto);
        assertThat(postPhotoRepository.findById(postPhoto.getId()).get()).isEqualTo(postPhoto);
    }

    @Test
    public void whenSaveThenDelete() {
        User user = new User(0, "login", "password");
        userRepository.save(user);
        Engine engine = new Engine(0, 180, 2.4, Fuel.PETROL);
        Car car = new Car(0, "Mitsubishi", "Outlander XL", 2010, engine, Transmission.CVT, WheelDrive.FULL_WD, Color.BLACK, Type.CROSSOVER, 97000);
        Post post = new Post(0, "testDescription", LocalDateTime.of(2022, 2, 1, 12, 50), BigDecimal.valueOf(1250000), user, car, new ArrayList<>(), true);
        postRepository.save(post);
        PostPhoto postPhoto = new PostPhoto(0, "new Photo", new byte[]{1, 2}, post.getId());
        postPhotoRepository.save(postPhoto);
        assertThat(postPhotoRepository.delete(postPhoto.getId())).isTrue();
        assertThat(postPhotoRepository.delete(postPhoto.getId())).isFalse();
        assertThat(postPhotoRepository.findById(postPhoto.getId())).isEmpty();
    }
}