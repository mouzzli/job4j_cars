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
import ru.job4j.cars.model.WheelDrive;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateWheelDriveServiceRepositoryTest {

    private static WheelDriveRepository wheelDriveRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        wheelDriveRepository = new HibernateWheelDriveRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM WheelDrive").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        WheelDrive savedWheelDrive = wheelDriveRepository.save(wheelDrive);
        assertThat(savedWheelDrive).isEqualTo(wheelDrive);
        Optional<WheelDrive> optionalWheelDrive = wheelDriveRepository.findById(savedWheelDrive.getId());
        assertThat(optionalWheelDrive.get()).isEqualTo(wheelDrive);
    }

    @Test
    public void whenSaveThenDelete() {
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        assertThat(wheelDriveRepository.delete(wheelDrive.getId())).isTrue();
        assertThat(wheelDriveRepository.delete(wheelDrive.getId())).isFalse();
        assertThat(wheelDriveRepository.findById(wheelDrive.getId())).isEmpty();

    }

    @Test
    public void whenSaveThenUpdate() {
        WheelDrive wheelDrive = new WheelDrive(0, "полный");
        wheelDriveRepository.save(wheelDrive);
        wheelDrive.setName("передний");
        assertThat(wheelDriveRepository.update(wheelDrive)).isTrue();
        assertThat(wheelDriveRepository.findById(wheelDrive.getId()).get()).isEqualTo(wheelDrive);
        wheelDrive.setId(0);
        assertThat(wheelDriveRepository.update(wheelDrive)).isFalse();
    }
}