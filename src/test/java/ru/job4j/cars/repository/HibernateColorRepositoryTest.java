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
import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateColorRepositoryTest {
    private static ColorRepository colorRepository;
    private static SessionFactory sf;

    @BeforeAll
    public static void initRepositories() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        colorRepository = new HibernateColorRepository(crudRepository);
    }

    @AfterEach
    public void clear() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Color").executeUpdate();
        Transaction tr = session.getTransaction();
        tr.commit();
        session.close();
    }

    @Test
    public void whenSaveThenFindById() {
        Color color = new Color(0, "серебро");
        Color savedColor = colorRepository.save(color);
        assertThat(savedColor).isEqualTo(color);
        Optional<Color> optionalColor = colorRepository.findById(savedColor.getId());
        assertThat(optionalColor.get()).isEqualTo(savedColor);
    }

    @Test
    public void whenSaveThenUpdate() {
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        color.setName("черный");
        assertThat(colorRepository.update(color)).isTrue();
        assertThat(colorRepository.findById(color.getId()).get()).isEqualTo(color);
        color.setId(0);
        assertThat(colorRepository.update(color)).isFalse();
    }

    @Test
    public void whenSaveThenDelete() {
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        assertThat(colorRepository.delete(color.getId())).isTrue();
        assertThat(colorRepository.delete(color.getId())).isFalse();
        assertThat(colorRepository.findById(color.getId())).isEmpty();

    }

    @Test
    public void whenSaveThenFindAll() {
        Color color = new Color(0, "серебро");
        colorRepository.save(color);
        Color color2 = new Color(0, "красный");
        colorRepository.save(color2);
        List<Color> colorList = colorRepository.findAll();
        assertThat(colorList).hasSameElementsAs(List.of(color, color2));
    }
}