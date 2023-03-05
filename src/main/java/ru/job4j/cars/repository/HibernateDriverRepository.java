package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Driver;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateDriverRepository implements DriverRepository {
    private static final String FIND_BY_ID = "FROM Driver "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Driver "
            + "WHERE id = :id";
    private CrudRepository crudRepository;

    @Override
    public Driver save(Driver driver) {
        crudRepository.tx(session -> session.save(driver));
        return driver;
    }

    @Override
    public Optional<Driver> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Driver.class, Map.of("id", id));
    }

    @Override
    public boolean update(Driver driver) {
        return crudRepository.tx(session -> {
            Driver savedDriver = session.get(Driver.class, driver.getId());
            if (savedDriver != null) {
                session.merge(driver);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }
}
