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
        crudRepository.tx(session -> session.save(post));
        return post;
    }

    @Override
    public boolean update(Post post) {
        return crudRepository.tx(session -> {
            Post savedPost = session.get(Post.class, post.getId());
            if (savedPost != null) {
                session.merge(post);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.update(DELETE_BY_ID, Map.of("id", id));
    }

    @Override
    public Optional<Post> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Post.class, Map.of("id", id));
    }
}
