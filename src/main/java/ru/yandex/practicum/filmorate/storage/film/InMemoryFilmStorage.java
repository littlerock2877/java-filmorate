package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    private int counter;

    @Override
    public Film addFilm(Film film) {
        int id = generateId();
        film.setId(id);
        film.setLikedUsersIds(new HashSet<>());
        films.put(id, film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        int id = film.getId();
        if (!films.containsKey(id)) {
            throw new NotFoundException(String.format("Film with id %d doesn't exist", id));
        }
        Film existingFilm = films.get(id);
        film.setLikedUsersIds(existingFilm.getLikedUsersIds());
        films.put(id, film);
        return film;
    }

    @Override
    public Film getFilmById(int filmId) {
        return Optional.ofNullable(films.get(filmId)).orElseThrow(() -> new NotFoundException(String.format("Film with id %d doesn't exist", filmId)));
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private int generateId() {
        return ++counter;
    }
}