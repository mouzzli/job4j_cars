package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.EngineRepository;
import ru.job4j.cars.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private PostRepository postRepository;
    private CarRepository carRepository;
    private EngineRepository engineRepository;

    @Override
    @Transactional
    public Post save(Post post, Car car, Engine engine) {
        engineRepository.save(engine);
        car.setEngine(engine);
        carRepository.save(car);
        post.setCar(car);
        User user = new User();
        user.setId(1);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public boolean delete(int id) {
        return postRepository.delete(id);
    }
}
