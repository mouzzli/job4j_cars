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
import ru.job4j.cars.model.Transmission;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateTransmissionRepositoryTest {

    private static TransmissionRepository transmissionRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        transmissionRepository = new HibernateTransmissionRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Transmission").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        Transmission transmission = new Transmission(0, "механическая");
        Transmission savedTransmission = transmissionRepository.save(transmission);
        assertThat(savedTransmission).isEqualTo(transmission);
        Optional<Transmission> optionalTransmission = transmissionRepository.findById(savedTransmission.getId());
        assertThat(optionalTransmission.get()).isEqualTo(savedTransmission);
    }

    @Test
    public void whenSaveThenDelete() {
        Transmission transmission = new Transmission(0, "механическая");
        transmissionRepository.save(transmission);
        assertThat(transmissionRepository.delete(transmission.getId())).isTrue();
        assertThat(transmissionRepository.delete(transmission.getId())).isFalse();
        assertThat(transmissionRepository.findById(transmission.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        Transmission transmission = new Transmission(0, "механическая");
        transmissionRepository.save(transmission);
        transmission.setName("автоматическая");
        assertThat(transmissionRepository.update(transmission)).isTrue();
        assertThat(transmissionRepository.findById(transmission.getId()).get()).isEqualTo(transmission);
        transmission.setId(0);
        assertThat(transmissionRepository.update(transmission)).isFalse();
    }

    @Test
    public void whenSaveThenFindAll() {
        Transmission transmission = new Transmission(0, "механическая");
        transmissionRepository.save(transmission);
        Transmission transmission2 = new Transmission(0, "вариатор");
        transmissionRepository.save(transmission2);
        List<Transmission> transmissionList = transmissionRepository.findAll();
        assertThat(transmissionList).hasSameElementsAs(List.of(transmission, transmission2));
    }
}