package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmStorage;

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addLike(int filmId, int userId) {
        return filmStorage.addLike(filmId, userId);
    }

    public Film removeLike(int filmId, int userId) {
        return filmStorage.removeLike(filmId, userId);
    }

    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getMostPopularFilms(count);
    }
}
