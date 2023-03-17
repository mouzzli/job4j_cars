package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PostPhoto;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostPhotoRepository implements PostPhotoRepository {
    private static final String FIND_BY_ID = "FROM PostPhoto "
            + "WHERE id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM PostPhoto "
            + "WHERE id = :id";

    private CrudRepository crudRepository;

    @Override
    public PostPhoto save(PostPhoto postPhoto) {
        crudRepository.run(session -> session.persist(postPhoto));
        return postPhoto;
    }

    @Override
    public Optional<PostPhoto> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, PostPhoto.class, Map.of("id", id));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }
}
