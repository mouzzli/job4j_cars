package ru.job4j.cars.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private UserService userService;
    private UserController userController;

    @BeforeEach
    public void initUserController() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenGetLoginForm() {
        String page = userController.getLoginForm();
        assertThat(page).isEqualTo("user/login");
    }

    @Test
    public void whenGetRegistrationForm() {
        String page = userController.getRegistrationForm();
        assertThat(page).isEqualTo("user/registration");
    }

    @Test
    public void whenRegistration() {
        User user = new User(0, "login", "password");
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(userService.save(user)).thenReturn(user);
        String page = userController.registration(user, request);
        Mockito.verify(session).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/index");
    }

    @Test
    public void whenLoginSuccess() {
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User();
        user.setId(21);
        user.setLogin("login");
        user.setPassword("password");
         Mockito.when(userService.findByLoginAndPassword(user.getLogin(), user.getPassword()))
                .thenReturn(Optional.of(user));
        Mockito.when(request.getSession()).thenReturn(session);
        String page = userController.login(user, request, model);
        Mockito.verify(session).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/index");
    }

    @Test
    public void whenLoginFail() {
        Model model = Mockito.mock(Model.class);
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        Mockito.when(userService.findByLoginAndPassword(user.getLogin(), user.getPassword()))
                .thenReturn(Optional.empty());
        String page = userController.login(user, request, model);
        Mockito.verify(model).addAttribute("error", "Пользователь с таким логином или паролем не найден!");
        assertThat(page).isEqualTo("user/login");
    }

    @Test
    public void whenHandlerRegistrationUser() {
        Model model = Mockito.mock(Model.class);
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        String page = userController.handlerRegistrationUser(exception, model);
        Mockito.verify(model).addAttribute("error", "Пользователь с таким логином уже существует!");
        assertThat(page).isEqualTo("user/registration");
    }

    @Test
    public void whenLogout() {
        HttpSession session = Mockito.mock(HttpSession.class);
        String page = userController.logout(session);
        Mockito.verify(session).invalidate();
        assertThat(page).isEqualTo("redirect:/login");
    }
}