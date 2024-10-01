package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final FilmRowMapper filmRowMapper;
    private final GenreRowMapper genreRowMapper;
    private final UserRowMapper userRowMapper;

    @Override
    public Film addFilm(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int ratingId = film.getMpa().getId();
        String sqlQuery = String.format("INSERT INTO films (name, description, release_date, duration, rating_id) VALUES (:name, :description, :releaseDate, :duration, %d)", ratingId);
        jdbc.batchUpdate(sqlQuery, SqlParameterSourceUtils.createBatch(film), keyHolder);
        film.setId(keyHolder.getKeyAs(Integer.class));
        updateGenres(film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET name=':name', description=':description', release_date=:release_date, duration=:duration, rating_id=:rating_id WHERE id=:id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("rating_id", film.getMpa().getId());
        mapSqlParameterSource.addValue("name", film.getName());
        mapSqlParameterSource.addValue("description", film.getDescription());
        mapSqlParameterSource.addValue("release_date", film.getReleaseDate());
        mapSqlParameterSource.addValue("duration", film.getDuration());
        mapSqlParameterSource.addValue("id", film.getId());
        jdbc.update(sqlQuery, mapSqlParameterSource);
        sqlQuery = "DELETE FROM film_genre WHERE film_id=:id";
        jdbc.update(sqlQuery, mapSqlParameterSource);
        updateGenres(film);
        return film;
    }

    @Override
    public Film getFilmById(int filmId) {
        String sql = String.format("SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_id, m.name AS rating FROM films AS f JOIN mpa AS m ON f.rating_id=m.id WHERE f.id=%d;", filmId);
        List<Film> result = jdbc.query(sql, filmRowMapper);
        if (result.isEmpty()) {
            throw new NotFoundException(String.format("Film with id %d doesn't exist", filmId));
        }
        Film film = result.get(0);
        film.setLikedUsersIds(getLikedUsers(filmId).stream().map(User::getId).collect(Collectors.toSet()));
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_id, m.name AS rating FROM films AS f JOIN mpa AS m ON f.rating_id=m.id;";
        return jdbc.query(sql, filmRowMapper);
    }

    @Override
    public List<Film> getMostPopularFilms(int count) {
        String sql = String.format("SELECT f.id, f.name, f.description, f.release_date, f.duration, f.rating_id, m.name AS rating, COUNT(*) AS c FROM films AS f JOIN mpa AS m ON f.rating_id=m.id JOIN film_likes AS fl ON fl.film_id=f.id GROUP BY f.id ORDER BY c DESC LIMIT %d;", count);
        return jdbc.query(sql, filmRowMapper);
    }

    @Override
    public Film addLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        String sqlQueryForGenres = "INSERT INTO film_likes(film_id, user_id) VALUES (:film_id, :user_id)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", filmId);
        mapSqlParameterSource.addValue("user_id", userId);
        jdbc.update(sqlQueryForGenres, mapSqlParameterSource);
        film.getLikedUsersIds().add(userId);
        return film;
    }

    @Override
    public Film removeLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        String sqlQueryForGenres = "DELETE FROM film_likes WHERE user_id=:user_id AND film_id=:film_id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", filmId);
        mapSqlParameterSource.addValue("user_id", userId);
        jdbc.update(sqlQueryForGenres, mapSqlParameterSource);
        film.getLikedUsersIds().remove(userId);
        return film;
    }

    private void updateGenres(Film film) {
        if (film.getGenres() != null) {
            String sqlQueryForGenres = "INSERT INTO film_genre(film_id, genre_id) VALUES (:filmId, :genreId)";
            ArrayList<Genre> filmGenres = new ArrayList<>(film.getGenres());
            SqlParameterSource[] batch = new SqlParameterSource[filmGenres.size()];
            for (int i = 0; i < filmGenres.size(); i++) {
                MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                mapSqlParameterSource.addValue("filmId", film.getId());
                mapSqlParameterSource.addValue("genreId", filmGenres.get(i).getId());
                batch[i] = mapSqlParameterSource;
            }
            jdbc.batchUpdate(sqlQueryForGenres, batch);
        } else  {
            film.setGenres(new LinkedHashSet<>());
        }
    }

    private List<User> getLikedUsers(int filmId) {
        String sqlQuery = String.format("SELECT u.id, u.email, u.login, u.user_name, u.birthday FROM film_likes AS fl JOIN users AS u ON u.id = fl.user_id WHERE fl.film_id=%d", filmId);
        return jdbc.query(sqlQuery, userRowMapper);
    }
}