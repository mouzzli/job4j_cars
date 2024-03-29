package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private static final String FIND_BY_LAST_DAY = "FROM Post as p "
            + "WHERE p.created BETWEEN :after AND :before";
    private static final String FIND_BY_PHOTO = "FROM Post as p "
            + "WHERE p.postPhotos.size > 0";
    private static final String FIND_BY_BRAND = "FROM Post as p "
            + "LEFT JOIN FETCH p.postPhotos "
            + "WHERE p.car.brand = :brand";
    private static final String FIND_BY_ID = "FROM Post p "
            + "LEFT JOIN FETCH p.postPhotos "
            + "WHERE p.id = :id";
    private static final String DELETE_BY_ID = "DELETE FROM Post "
            + "WHERE id = :id";
    private static final String FIND_ALL = "SELECT DISTINCT p FROM Post p "
            + "LEFT JOIN FETCH p.postPhotos";
    private static final String CHANGE_STATUS = "UPDATE FROM Post "
            + "SET isActive = :status "
            + "WHERE id = :id";
    private CrudRepository crudRepository;

    @Override
    public List<Post> findByLastDay() {
        LocalDateTime before = LocalDateTime.now();
        LocalDateTime after = before.minusDays(1);
        return crudRepository.query(FIND_BY_LAST_DAY, Post.class, Map.of("after", after, "before", before));
    }

    @Override
    public List<Post> findWithPhoto() {
        return crudRepository.query(FIND_BY_PHOTO, Post.class);
    }

    @Override
    public List<Post> findByBrand(String brand) {
        return crudRepository.query(FIND_BY_BRAND, Post.class, Map.of("brand", brand));
    }

    @Override
    public Post save(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Post.class, Map.of("id", id));
    }

    @Override
    public List<Post> findAll() {
        return crudRepository.query(FIND_ALL, Post.class);
    }

    @Override
    public boolean changeStatus(boolean status, int postId) {
        return crudRepository.update(CHANGE_STATUS, Map.of("status", status, "id", postId));
    }
}
