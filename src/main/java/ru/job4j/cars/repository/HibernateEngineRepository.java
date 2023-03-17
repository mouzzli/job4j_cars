package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {
    private static final String FIND_BY_ID = "FROM Engine "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Engine "
            + "WHERE id = :id";
    private CrudRepository crudRepository;

    @Override
    public Engine save(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
        return engine;
    }

    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Engine.class, Map.of("id", id));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }

    @Override
    public boolean update(Engine engine) {
        return crudRepository.tx(session -> {
            Engine savedEngine = session.get(Engine.class, engine.getId());
            if (savedEngine != null) {
                session.merge(engine);
                return true;
            }
            return false;
        });
    }
}
