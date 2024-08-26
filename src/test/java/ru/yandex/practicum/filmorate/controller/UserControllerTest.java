package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserControllerTest")
public class UserControllerTest {
    private UserController userController;

    @BeforeEach
    public void beforeEach() {
        UserStorage userStorage = new InMemoryUserStorage();
        userController = new UserController(new UserService(userStorage));
    }

    @Test
    @DisplayName("Should create new valid user")
    public void shouldCreateNewValidUser() {
        User user = new User(0, "user@gmail.com", "user", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        userController.createUser(user);
        assertTrue(containsUser(user), "users list doesn't contains created user");
    }

    @Test
    @DisplayName("Should get all users")
    public void shouldGetAllUsers() {
        User user = new User(0, "user@gmail.com", "user", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        userController.createUser(user);
        List<User> savedUsers = userController.getAllUsers();
        assertFalse(savedUsers.isEmpty(), "saved users list is empty");
    }

    private boolean containsUser(User user) {
        Optional<User> savedUserOpt = userController.getAllUsers().stream()
                .filter(savedUser -> savedUser.getEmail().equals(user.getEmail()) &&
                        savedUser.getLogin().equals(user.getLogin()) &&
                        savedUser.getName().equals(user.getName()) &&
                        savedUser.getBirthday().equals(user.getBirthday()))
                .findAny();
        return savedUserOpt.isPresent();
    }
}