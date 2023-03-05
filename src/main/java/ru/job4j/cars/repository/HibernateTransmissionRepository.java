package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTransmissionRepository implements TransmissionRepository {
    private static final String FIND_BY_ID = "FROM Transmission "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Transmission "
            + "WHERE id = :id";
    private CrudRepository crudRepository;

    @Override
    public Transmission save(Transmission transmission) {
         crudRepository.tx(session -> session.save(transmission));
        return transmission;
    }

    @Override
    public Optional<Transmission> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Transmission.class, Map.of("id", id));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }

    @Override
    public boolean update(Transmission transmission) {
            return crudRepository.tx(session -> {
            Transmission savedTransmission = session.get(Transmission.class, transmission.getId());
            if (savedTransmission != null) {
                session.merge(transmission);
                return true;
            }
            return false;
        });
    }
}
