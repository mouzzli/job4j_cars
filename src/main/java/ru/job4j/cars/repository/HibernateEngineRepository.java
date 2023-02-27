package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class HibernateEngineRepository implements EngineRepository {
    private CrudRepository crudRepository;
}
