package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreRowMapper mapper;

    @Override
    public Optional<Genre> findGenreById(int genreId) {
        String sqlQuery = "SELECT id, genre FROM genre WHERE id=:id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", genreId);
        List<Genre> result = jdbc.query(sqlQuery, mapSqlParameterSource, mapper);
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
}
