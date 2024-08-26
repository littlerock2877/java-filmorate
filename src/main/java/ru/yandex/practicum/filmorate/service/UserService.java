package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

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
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        Set<Integer> userFriendsIds = user.getFriendsIds();
        Set<Integer> friendFriendsIds = friend.getFriendsIds();
        userFriendsIds.add(friendId);
        friendFriendsIds.add(userId);
        user.setFriendsIds(userFriendsIds);
        friend.setFriendsIds(friendFriendsIds);
        return user;
    }

    public User removeFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        Set<Integer> userFriendsIds = user.getFriendsIds();
        Set<Integer> friendFriendsIds = friend.getFriendsIds();
        userFriendsIds.remove(friendId);
        friendFriendsIds.remove(userId);
        user.setFriendsIds(userFriendsIds);
        friend.setFriendsIds(friendFriendsIds);
        return user;
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        User firstUser = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);
        return userStorage.getAllUsers().stream()
                .filter(user -> firstUser.getFriendsIds().contains(user.getId()) && friend.getFriendsIds().contains(user.getId()))
                .collect(Collectors.toList());
    }
}