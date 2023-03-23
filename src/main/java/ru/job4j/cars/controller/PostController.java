package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
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
        return "post/addPost";
    }

    @PostMapping("/addPost")
    public String addPost(@ModelAttribute Car car,
                          @ModelAttribute Engine engine,
                          @ModelAttribute Post post,
                          @SessionAttribute User user,
                          @RequestParam(value = "photos") List<MultipartFile> photos) throws Exception {
        postService.save(post, car, engine, user, photos);
        return "redirect:/index";
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam("status") boolean status, @RequestParam("post.id") int postId) {
        postService.changeStatus(status, postId);
        return "redirect:/index";
    }
}
