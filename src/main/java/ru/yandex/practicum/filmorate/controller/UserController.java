package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@RestController
@RequestMapping("users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        log.info("Creating user {} - Started", user);
        user = userService.createUser(user);
        log.info("Creating user {} - Finished", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Updating user {} - Started", user);
        user = userService.updateUser(user);
        log.info("Updating user {} - Finished", user);
        return user;
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addToFriendList(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Adding friend {} to user {} - Started", friendId, userId);
        User user = userService.addFriend(userId, friendId);
        log.info("Adding friend {} to user {} - user friendList {}", friendId, userId, user.getFriendsIds());
        return user;
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User removeFromFriendList(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Removing friend {} from user {} - Started", friendId, userId);
        User user = userService.removeFriend(userId, friendId);
        log.info("Removing friend {} to user {} - user friend list {}", friendId, userId, user.getFriendsIds());
        return user;
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUsersFriends(@PathVariable int userId) {
        log.info("Getting user {} friend list - Started", userId);
        List<User> friendList = userService.getUserFriends(userId);
        log.info("Getting user {} friend list - {}", userId, friendList.toString());
        return friendList;
    }

    @GetMapping("{userId}/friends/common/{otherId}")
    public List<User> getUsersCommonFriends(@PathVariable int userId, @PathVariable int otherId) {
        log.info("Getting common friends between user {} and user {} - Started", userId, otherId);
        List<User> commonFriendsList = userService.getCommonFriends(userId, otherId);
        log.info("Getting common friends between user {} and user {} - {}", userId, otherId, commonFriendsList.toString());
        return commonFriendsList;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        log.info("Getting user with id {} - Started", userId);
        User user = userService.getUserById(userId);
        log.info("Getting user with id {} - User = {}", userId, user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Getting all users - Started");
        List<User> allUsers = userService.getAllUsers();
        log.info("Getting all users - {}", allUsers.toString());
        return allUsers;
    }
}