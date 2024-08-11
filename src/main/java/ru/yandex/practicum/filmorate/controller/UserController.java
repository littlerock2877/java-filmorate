package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    private int counter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        log.info("Creating user {} - Started", user);
        int id = generateId();
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.put(id, user);
        log.info("Creating user {} - Finished", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Updating user {} - Started", user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Updating user {} - Finished", user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private int generateId() {
        return counter++;
    }
}