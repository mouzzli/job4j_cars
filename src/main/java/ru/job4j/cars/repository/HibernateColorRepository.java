package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateColorRepository implements ColorRepository {
    private static final String FIND_BY_ID = "FROM Color "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Color "
            + "WHERE id = :id";
    private static final String FIND_ALL = "FROM Color";
    private CrudRepository crudRepository;

    @Override
    public Color save(Color color) {
        crudRepository.run(session -> session.persist(color));
        return color;
    }

    @Override
    public Optional<Color> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Color.class, Map.of("id", id));
    }

    @Override
    public boolean update(Color color) {
        return crudRepository.tx(session -> {
            Color savedColor = session.get(Color.class, color.getId());
            if (savedColor != null) {
                session.merge(color);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }

    @Override
    public List<Color> findAll() {
        return crudRepository.query(FIND_ALL, Color.class);
    }
}
