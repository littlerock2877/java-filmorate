package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();

    private int counter;

    @Override
    public User createUser(User user) {
        int id = generateId();
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setFriendsIds(new HashSet<>());
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        int id = user.getId();
        if (!users.containsKey(id)) {
            throw new NotFoundException(String.format("User with id %d doesn't exist", id));
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        User existingUser = users.get(id);
        user.setFriendsIds(existingUser.getFriendsIds());
        users.put(id, user);
        return user;
    }

    @Override
    public User getUserById(int id) {
        return Optional.ofNullable(users.get(id)).orElseThrow(() -> new NotFoundException(String.format("User with id %d doesn't exist", id)));
    }

    @Override
    public List<User> getUserFriends(int id) {
        User foundUser = getUserById(id);
        return users.entrySet().stream()
                .filter(user -> foundUser.getFriendsIds().contains(user.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    private int generateId() {
        return ++counter;
    }
}