package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper mapper;

    @Override
    public Optional<Genre> findGenreById(int genreId) {
        String sqlQuery = "SELECT id, genre FROM genre WHERE id=?";
        List<Genre> result = jdbc.query(sqlQuery, mapper, genreId);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(result.get(0));
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "SELECT id, genre FROM genre";
        return jdbc.query(sqlQuery, mapper);
    }

    @Override
    public void load(List<Film> films) {
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final Map<Integer, Film> filmById = films.stream().collect(Collectors.toMap(Film::getId, identity()));
        final String sqlQuery = "SELECT * FROM genre AS g JOIN film_genre AS fg ON fg.genre_id = g.id WHERE fg.film_id IN (" + inSql + ")";
        jdbc.query(sqlQuery, (rs) -> {
            final Film film = filmById.get(rs.getInt("film_id"));
            film.addGenre(makeGenre(rs));
        }, films.stream().map(Film::getId).toArray());
    }

    static Genre makeGenre(ResultSet rs) throws SQLException {
        return new Genre(
                rs.getInt("id"),
                rs.getString("genre"));
    }
}
