package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmRepository;
import ru.yandex.practicum.filmorate.storage.genre.GenreRepository;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmStorage;
    private final MpaRepository mpaStorage;
    private final GenreRepository genreStorage;

    public Film addFilm(Film film) {
        int ratingId = film.getMpa().getId();
        mpaStorage.findMpaById(ratingId).orElseThrow(() -> new IllegalArgumentException(String.format("Mpa with id %d doesn't exists", ratingId)));
        if (film.getGenres() == null) {
            film.setGenres(new LinkedHashSet<>());
        }
        if (!new HashSet<>(genreStorage.getAllGenres().stream().map(Genre::getId).toList()).containsAll(film.getGenres().stream().map(Genre::getId).toList())) {
            throw new IllegalArgumentException("Invalid genre id");
        }
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        getFilmById(film.getId());
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        final Film film = filmStorage.getFilmById(id);
        genreStorage.load(Collections.singletonList(film));
        return film;
    }

    public List<Film> getAllFilms() {
        final List<Film> films = filmStorage.getAllFilms();
        genreStorage.load(films);
        return films;
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
