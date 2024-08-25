package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        userStorage.getUserById(userId);
        Set<Integer> likedUsersIds = film.getLikedUsersIds();
        likedUsersIds.add(userId);
        film.setLikedUsersIds(likedUsersIds);
        return film;
    }

    public Film removeLike(int filmId, int userId){
        Film film = filmStorage.getFilmById(filmId);
        userStorage.getUserById(userId);
        Set<Integer> likedUsersIds = film.getLikedUsersIds();
        likedUsersIds.remove(userId);
        film.setLikedUsersIds(likedUsersIds);
        return film;
    }

    public List<Film> getMostPopularFilms(int count) {
        List<Film> films = filmStorage.getAllFilms();
        films.sort(Comparator.comparing(film -> film.getLikedUsersIds().size(), Comparator.reverseOrder()));
        return films.stream()
                .limit(count)
                .collect(Collectors.toList());
    }
}
