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
import ru.job4j.cars.model.Type;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateTypeRepositoryTest {
    private static TypeRepository typeRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        typeRepository = new HibernateTypeRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Type").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        Type type = new Type(0, "универсал");
        Type savedType = typeRepository.save(type);
        assertThat(savedType).isEqualTo(type);
        Optional<Type> optionalType = typeRepository.findById(savedType.getId());
        assertThat(optionalType.get()).isEqualTo(savedType);
    }

    @Test
    public void whenSaveThenDelete() {
        Type type = new Type(0, "универсал");
        typeRepository.save(type);
        assertThat(typeRepository.delete(type.getId())).isTrue();
        assertThat(typeRepository.delete(type.getId())).isFalse();
        assertThat(typeRepository.findById(type.getId())).isEmpty();
    }

    @Test
    public void whenSaveThenUpdate() {
        Type type = new Type(0, "универсал");
        typeRepository.save(type);
        type.setName("седан");
        assertThat(typeRepository.update(type)).isTrue();
        assertThat(typeRepository.findById(type.getId()).get()).isEqualTo(type);
        type.setId(0);
        assertThat(typeRepository.update(type)).isFalse();
    }
}