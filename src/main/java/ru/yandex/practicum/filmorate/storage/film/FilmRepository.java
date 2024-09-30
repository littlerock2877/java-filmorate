package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmRepository {
    List<Film> getAllFilms();

    Film getFilmById(int filmId);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getMostPopularFilms(int count);

    Film addLike(int filmId, int userId);

    Film removeLike(int filmId, int userId);
}
