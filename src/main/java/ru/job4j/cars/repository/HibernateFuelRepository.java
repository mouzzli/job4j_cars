package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Fuel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateFuelRepository implements FuelRepository {
    private static final String FIND_BY_ID = "FROM Fuel "
            + "WHERE id = :id";
    private static final String DELETE_FROM_ID = "DELETE FROM Fuel "
            + "WHERE id = :id";
    private static final String FIND_ALL = "FROM Fuel";
    private CrudRepository crudRepository;

    @Override
    public Fuel save(Fuel fuel) {
        crudRepository.run(session -> session.persist(fuel));
        return fuel;
    }

    @Override
    public Optional<Fuel> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Fuel.class, Map.of("id", id));
    }

    @Override
    public boolean update(Fuel fuel) {
        return crudRepository.tx(session -> {
            Fuel savedFuel = session.get(Fuel.class, fuel.getId());
            if (savedFuel != null) {
                session.merge(fuel);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_FROM_ID, Map.of("id", id));
    }

    @Override
    public List<Fuel> findAll() {
        return crudRepository.query(FIND_ALL, Fuel.class);
    }
}
