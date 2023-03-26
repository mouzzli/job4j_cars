package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.mappper.PostMapper;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.EngineRepository;
import ru.job4j.cars.repository.PostPhotoRepository;
import ru.job4j.cars.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SimplePostService implements PostService {
    private PostRepository postRepository;
    private PostPhotoRepository postPhotoRepository;
    private EngineRepository engineRepository;

    @Override
    @Transactional
    public Post save(Post post, Car car, Engine engine, User user, List<MultipartFile> photos) {
        var optionalEngine = engineRepository.exist(engine);
        optionalEngine.ifPresent(value -> engine.setId(value.getId()));
        car.setEngine(engine);
        post.setCar(car);
        post.setUser(user);
        var savedPost = postRepository.save(post);

        photos.forEach(multipartFile -> {
            if (!multipartFile.isEmpty()) {
                try {
                    postPhotoRepository.save(new PostPhoto(0, multipartFile.getOriginalFilename(), multipartFile.getBytes(), savedPost.getId()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return savedPost;
    }

    @Override
    public boolean delete(int id) {
        return postRepository.delete(id);
    }

    @Override
    public List<PostDto> findAll() {
        return PostMapper.INSTANCE.toPostDto(postRepository.findAll());
    }

    @Override
    public boolean changeStatus(boolean status, int postId) {
        return postRepository.changeStatus(status, postId);
    }
}
