package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PostControllerTest {
    private ColorService colorService;
    private TransmissionService transmissionService;
    private WheelDriveService wheelDriveService;
    private FuelService fuelService;
    private TypeService typeService;
    private PostService postService;
    private PostController postController;

    @BeforeEach
    public void initPostController() {
        colorService = Mockito.mock(ColorService.class);
        transmissionService = Mockito.mock(TransmissionService.class);
        wheelDriveService = Mockito.mock(WheelDriveService.class);
        fuelService = Mockito.mock(FuelService.class);
        typeService = Mockito.mock(TypeService.class);
        postService = Mockito.mock(PostService.class);
        postController = new PostController(colorService, transmissionService, wheelDriveService, fuelService, typeService, postService);
    }

    @Test
    public void whenCreatePost() {
        Model model = Mockito.mock(Model.class);
        String page = postController.getPostForm(model);
        Mockito.verify(model).addAttribute("colors", colorService.findAll());
        Mockito.verify(model).addAttribute("transmissions", transmissionService.findAll());
        Mockito.verify(model).addAttribute("wheelDrives", wheelDriveService.findAll());
        Mockito.verify(model).addAttribute("fuels", fuelService.findAll());
        Mockito.verify(model).addAttribute("types", typeService.findAll());
        assertThat(page).isEqualTo("post/addPost");
    }

    @Test
    public void whenAddPost() throws Exception {
        Post post = new Post();
        post.setId(2);
        Car car = new Car();
        car.setId(1);
        User user = new User();
        user.setId(12);
        Engine engine = new Engine();
        engine.setId(4);
        List<MultipartFile> photos = new ArrayList<>();
        String page = postController.addPost(post, car, user, engine, photos);
        Mockito.verify(postService).save(post, car, engine, user, photos);
        assertThat(page).isEqualTo("redirect:/index");
    }

    @Test
    public void whenChangeStatus() {
        boolean status = false;
        int postId = 14;
        String page = postController.changeStatus(status, postId);
        Mockito.verify(postService).changeStatus(status, postId);
        assertThat(page).isEqualTo("redirect:/index");
    }
}