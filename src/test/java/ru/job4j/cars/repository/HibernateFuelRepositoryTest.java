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
import ru.job4j.cars.model.Fuel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateFuelRepositoryTest {
    private static FuelRepository fuelRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        fuelRepository = new HibernateFuelRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Fuel").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindAll() {
        Fuel fuel = new Fuel(0, "бензиновый");
        Fuel savedFuel = fuelRepository.save(fuel);
        Fuel fuel2 = new Fuel(0, "дизельный");
        fuelRepository.save(fuel2);
        assertThat(savedFuel).isEqualTo(fuel);
        List<Fuel> fuelList = fuelRepository.findAll();
        assertThat(fuelList).hasSameElementsAs(List.of(fuel, fuel2));
    }

    @Test
    public void whenSaveThenDelete() {
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        assertThat(fuelRepository.delete(fuel.getId())).isTrue();
        assertThat(fuelRepository.delete(fuel.getId())).isFalse();
        assertThat(fuelRepository.findById(fuel.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        Fuel fuel = new Fuel(0, "бензиновый");
        fuelRepository.save(fuel);
        fuel.setName("дизельный");
        assertThat(fuelRepository.update(fuel)).isTrue();
        assertThat(fuelRepository.findById(fuel.getId()).get()).isEqualTo(fuel);
        fuel.setId(0);
        assertThat(fuelRepository.update(fuel)).isFalse();
    }
}