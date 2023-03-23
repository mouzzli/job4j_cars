package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.service.PostService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {
    private PostService postService;
    private IndexController indexController;

    @BeforeEach
    public void initIndexController() {
        postService = Mockito.mock(PostService.class);
        indexController = new IndexController(postService);
    }

    @Test
    public void whenGetIndex() {
        List<PostDto> postDtoList = List.of(new PostDto(), new PostDto());
        Model model = Mockito.mock(Model.class);
        Mockito.when(postService.findAll()).thenReturn(postDtoList);
        String page = indexController.getIndex(model);
        Mockito.verify(model).addAttribute("posts", postDtoList);
        assertThat(page).isEqualTo("index");
    }

}