package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final UserRowMapper userRowMapper;

    @Override
    public User createUser(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlQuery = "INSERT INTO users (email, login, user_name, birthday) VALUES (:email, :login, :name, :birthday)";
        jdbc.batchUpdate(sqlQuery, SqlParameterSourceUtils.createBatch(user), keyHolder);
        user.setId(keyHolder.getKeyAs(Integer.class));
        return user;
    }

    @Override
    public User updateUser(User user) {
        getUserById(user.getId());
        String sqlQuery = "UPDATE users SET email=':email', login=':login', user_name=:user_name, birthday=:birthday WHERE id=:id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("login", user.getLogin());
        mapSqlParameterSource.addValue("user_name", user.getName());
        mapSqlParameterSource.addValue("birthday", user.getBirthday());
        mapSqlParameterSource.addValue("id", user.getId());
        jdbc.update(sqlQuery, mapSqlParameterSource);
        sqlQuery = "DELETE FROM film_likes WHERE user_id=:id";
        jdbc.update(sqlQuery, mapSqlParameterSource);
        return user;
    }

    @Override
    public User getUserById(int userId) {
        String sql = String.format("SELECT id, email, login user_name, birthday FROM users WHERE id=%d;", userId);
        List<User> result = jdbc.query(sql, userRowMapper);
        if (result.isEmpty()) {
            throw new NotFoundException(String.format("User with id %d doesn't exist", userId));
        }
        User user = result.get(0);
        user.setFriendsIds(getUserFriends(userId).stream().map(User::getId).collect(Collectors.toSet()));
        return user;
    }

    @Override
    public User addFriend(int userId, int friendId) {
        User user = getUserById(userId);
        getUserById(friendId);
        String sqlQuery = "INSERT INTO friendship (user_id, friend_id) VALUES (:user_id, :friend_id)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);
        mapSqlParameterSource.addValue("friend_id", friendId);
        jdbc.update(sqlQuery, mapSqlParameterSource);
        Set<Integer> userFriends = user.getFriendsIds();
        if (userFriends == null) {
            userFriends = new HashSet<>();
        }
        userFriends.add(friendId);
        return user;
    }

    @Override
    public User removeFriend(int userId, int friendId) {
        User user = getUserById(userId);
        getUserById(friendId);
        String sqlQuery = "DELETE FROM friendship WHERE user_id=:user_id AND friend_id=:friend_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);
        mapSqlParameterSource.addValue("friend_id", friendId);
        jdbc.update(sqlQuery, mapSqlParameterSource);
        Set<Integer> userFriends = user.getFriendsIds();
        if (userFriends == null) {
            userFriends = new HashSet<>();
        }
        userFriends.remove(friendId);
        return user;
    }

    @Override
    public List<User> getUserFriends(int userId) {
        if (getAllUsers().stream().noneMatch(u -> u.getId() == userId)) {
            throw new NotFoundException(String.format("User with id %d doesn't exist", userId));
        }
        String sqlQuery = "SELECT friend_id AS id, email, login, user_name, birthday FROM friendship AS f JOIN users AS u ON u.id=f.friend_id WHERE f.user_id=:user_id;";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", userId);
        return jdbc.query(sqlQuery, mapSqlParameterSource, userRowMapper);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT id, email, login user_name, birthday FROM users";
        return jdbc.query(sql, userRowMapper);
    }
}
