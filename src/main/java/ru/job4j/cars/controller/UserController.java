package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "user/login";
    }

    @GetMapping("/registrationForm")
    public String getRegistrationForm() {
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, HttpServletRequest request) {
        user = userService.save(user);
        var session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request, Model model) {
        var optionalUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (optionalUser.isPresent()) {
            var session = request.getSession();
            session.setAttribute("user", optionalUser.get());
            return "redirect:/index";
        }
        model.addAttribute("error", "Пользователь с таким логином или паролем не найден!");
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlerRegistrationUser(ConstraintViolationException e, Model model) {
        log.error("Ошибка добавления пользователя", e);
        model.addAttribute("error", "Пользователь с таким логином уже существует!");
        return "user/registration";
    }
}
