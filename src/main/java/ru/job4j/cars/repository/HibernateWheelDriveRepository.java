package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.WheelDrive;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateWheelDriveRepository implements WheelDriveRepository {
    private static final String FIND_BY_ID = "FROM WheelDrive "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM WheelDrive "
            + "where id = :id";

    private CrudRepository crudRepository;

    @Override
    public WheelDrive save(WheelDrive wheelDrive) {
        crudRepository.run(session -> session.save(wheelDrive));
        return wheelDrive;
    }

    @Override
    public Optional<WheelDrive> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, WheelDrive.class, Map.of("id", id));
    }

    @Override
    public boolean update(WheelDrive wheelDrive) {
        return crudRepository.tx(session -> {
            WheelDrive savedWheelDrive = session.get(WheelDrive.class, wheelDrive.getId());
            if (savedWheelDrive != null) {
                session.merge(wheelDrive);
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
