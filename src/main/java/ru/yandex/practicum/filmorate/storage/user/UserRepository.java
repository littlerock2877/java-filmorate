package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
public interface UserRepository {
    User createUser(User user);

    User updateUser(User user);

    User getUserById(int userId);

    List<User> getUserFriends(int userId);

    List<User> getAllUsers();

    User addFriend(int userId, int friendId);

    User removeFriend(int userId, int friendId);
}