package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcMpaRepository implements MpaRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final MpaRowMapper mapper;

    @Override
    public List<Mpa> getAllRatings() {
        String sqlQuery = "SELECT id, name FROM mpa";
        return jdbc.query(sqlQuery, mapper);
    }

    @Override
    public Optional<Mpa> findMpaById(int mpaId) {
        String sqlQuery = "SELECT id, name FROM mpa WHERE id=:id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", mpaId);
        List<Mpa> result = jdbc.query(sqlQuery, mapSqlParameterSource,mapper);
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(result.get(0));
    }
}