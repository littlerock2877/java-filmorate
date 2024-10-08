package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@RestController
@RequestMapping("films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("Adding film {} - Started", film);
        film = filmService.addFilm(film);
        log.info("Adding film {} - Finished", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("Updating film {} - Started", film);
        film = filmService.updateFilm(film);
        log.info("Updating film {} - Finished", film);
        return film;
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Adding like to film {} by user {} - Started", filmId, userId);
        Film film = filmService.addLike(filmId, userId);
        log.info("Adding like to film {} by user {} - Finished", filmId, userId);
        return film;
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Film removeLike(@PathVariable int filmId, @PathVariable int userId) {
        log.info("Removing like to film {} by user {} - Started", filmId, userId);
        Film film = filmService.removeLike(filmId, userId);
        log.info("Removing like to film {} by user {} - Finished", filmId, userId);
        return film;
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("Getting {} most popular films - Started", count);
        List<Film> mostPopularFilms = filmService.getMostPopularFilms(count);
        log.info("Getting {} most popular films - {}", count, mostPopularFilms.toString());
        return mostPopularFilms;
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable int filmId) {
        log.info("Getting film with id {} - Started", filmId);
        Film film = filmService.getFilmById(filmId);
        log.info("Getting film with id {} - Film = {}", filmId, film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Getting all films - Started");
        List<Film> allFilms = filmService.getAllFilms();
        log.info("Getting all films - {}", allFilms.toString());
        return allFilms;
    }
}