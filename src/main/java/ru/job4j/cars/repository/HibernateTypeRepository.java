package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Type;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTypeRepository implements TypeRepository {
    private static final String FIND_BY_ID = "FROM Type "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Type "
            + "WHERE id = :id";
    private CrudRepository crudRepository;

    @Override
    public Type save(Type type) {
        crudRepository.tx(session ->  session.save(type));
        return type;
    }

    @Override
    public Optional<Type> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Type.class, Map.of("id", id));
    }

    @Override
    public boolean update(Type type) {
        return crudRepository.tx(session -> {
            Type savedType = session.get(Type.class, type.getId());
            if (savedType != null) {
                session.merge(type);
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
