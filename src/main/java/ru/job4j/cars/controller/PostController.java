package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.*;

@Controller
@AllArgsConstructor
public class PostController {
    private ColorService colorService;
    private TransmissionService transmissionService;
    private WheelDriveService wheelDriveService;
    private FuelService fuelService;
    private TypeService typeService;
    private PostService postService;

    @GetMapping("/createPost")
    public String createPost(Model model) {
        model.addAttribute("colors", colorService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("wheelDrives", wheelDriveService.findAll());
        model.addAttribute("fuels", fuelService.findAll());
        model.addAttribute("types", typeService.findAll());
        return "addPost";
    }

    @PostMapping ("/addPost")
    public String addPost(@ModelAttribute Car car, @ModelAttribute Engine engine, @ModelAttribute Post post) {
        postService.save(post, car, engine);
        return "redirect:/";
    }
}
