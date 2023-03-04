package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {
    private static final String FIND_BY_LAST_DAY = "FROM Post as p "
            + "WHERE p.created BETWEEN :after AND :before";
    private static final String FIND_BY_PHOTO = "FROM Post as p "
            + "WHERE p.postPhotos.size > 0";
    private static final String FIND_BY_BRAND = "FROM Post as p "
            + "WHERE p.car.brand = :brand";
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
}
