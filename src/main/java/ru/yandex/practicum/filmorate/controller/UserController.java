package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@RestController
@RequestMapping("users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid User user) {
        log.info("Creating user {} - Started", user);
        user = userStorage.createUser(user);
        log.info("Creating user {} - Finished", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Updating user {} - Started", user);
        user = userStorage.updateUser(user);
        log.info("Updating user {} - Finished", user);
        return user;
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addToFriendList(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Adding friend {} to user {} - Started", friendId, userId);
        User user = userService.addFriend(userId, friendId);
        log.info("Adding friend {} to user {} - user friendList {}", friendId, userId, user.getFriendsIds());
        log.info("Adding friend {} to user {} - Finished", friendId, userId);
        return user;
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User removeFromFriendList(@PathVariable int userId, @PathVariable int friendId) {
        log.info("Removing friend {} from user {} - Started", friendId, userId);
        User user = userService.removeFriend(userId, friendId);
        log.info("Removing friend {} to user {} - user friend list {}", friendId, userId, user.getFriendsIds());
        log.info("Removing friend {} to user {} - Finished", friendId, userId);
        return user;
    }

    @GetMapping("/{userId}/friends")
    public List<User> getUsersFriends(@PathVariable int userId) {
        log.info("Getting user {} friend list - Started", userId);
        List<User> friendList = userStorage.getUserFriends(userId);
        log.info("Getting user {} friend list - {}", userId, friendList.toString());
        log.info("Getting user {} friend list - Finished", userId);
        return friendList;
    }

    @GetMapping("{userId}/friends/common/{otherId}")
    public List<User> getUsersCommonFriends(@PathVariable int userId, @PathVariable int otherId) {
        log.info("Getting common friends between user {} and user {} - Started", userId, otherId);
        List<User> commonFriendsList = userService.getCommonFriends(userId, otherId);
        log.info("Getting common friends between user {} and user {} - {}", userId, otherId, commonFriendsList.toString());
        log.info("Getting common friends between user {} and user {} - Finished", userId, otherId);
        return commonFriendsList;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable int userId) {
        log.info("Getting user with id {} - Started", userId);
        User user = userStorage.getUserById(userId);
        log.info("Getting user with id {} - User = {}", userId, user);
        log.info("Getting user with id {} - Finished", userId);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Getting all users - Started");
        List<User> allUsers = userStorage.getAllUsers();
        log.info("Getting all users - {}", allUsers.toString());
        log.info("Getting all users - Finished");
        return allUsers;
    }
}