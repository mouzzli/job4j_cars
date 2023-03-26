package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {
    private static final String FIND_BY_ID = "FROM Car "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Car "
            + "WHERE id = :id";
    private CrudRepository crudRepository;

    @Override
    public Car save(Car car) {
        crudRepository.run(session -> session.persist(car));
        return car;
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Car.class, Map.of("id", id));
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }
}
