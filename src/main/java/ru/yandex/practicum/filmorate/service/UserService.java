package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userStorage;

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getUserFriends(int userId) {
        return userStorage.getUserFriends(userId);
    }

    public User getUserById(int userId) {
        return userStorage.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User addFriend(int userId, int friendId) {
        return userStorage.addFriend(userId, friendId);
    }

    public User removeFriend(int userId, int friendId) {
        return userStorage.removeFriend(userId, friendId);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        userStorage.getUserById(userId);
        userStorage.getUserById(friendId);
        List<Integer> friends = userStorage.getUserFriends(friendId).stream().map(User::getId).toList();
        return userStorage.getUserFriends(userId).stream().filter(friend -> friends.contains(friend.getId())).toList();
    }
}