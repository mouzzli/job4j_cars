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
import ru.job4j.cars.model.Engine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateEngineRepositoryTest {

    private static EngineRepository engineRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        engineRepository = new HibernateEngineRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        Engine engine = new Engine(0, "4B12", 180, 2.4, "бензиновый");
        Engine savedEngine = engineRepository.save(engine);
        assertThat(savedEngine).isEqualTo(engine);
        Optional<Engine> optionalEngine = engineRepository.findById(savedEngine.getId());
        assertThat(optionalEngine.get()).isEqualTo(savedEngine);
    }

    @Test
    public void whenSaveThenDelete() {
        Engine engine = new Engine(0, "4B12", 180, 2.4, "бензиновый");
        engineRepository.save(engine);
        assertThat(engineRepository.delete(engine.getId())).isTrue();
        assertThat(engineRepository.delete(engine.getId())).isFalse();
        assertThat(engineRepository.findById(engine.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        Engine engine = new Engine(0, "4B12", 180, 2.4, "бензиновый");
        engineRepository.save(engine);
        engine.setName("test name");
        assertThat(engineRepository.update(engine)).isTrue();
        assertThat(engineRepository.findById(engine.getId()).get()).isEqualTo(engine);
        engine.setId(0);
        assertThat(engineRepository.update(engine)).isFalse();
    }
}