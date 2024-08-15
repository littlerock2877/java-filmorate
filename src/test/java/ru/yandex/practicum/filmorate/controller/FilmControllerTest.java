package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FilmControllerTest")
public class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
    }

    @Test
    @DisplayName("Should add new valid film")
    public void shouldAddNewValidFilm() {
        Film film = new Film(0, "Film name", "Film description", LocalDate.now(), 1200000);
        filmController.addFilm(film);
        assertTrue(containsFilm(film), "films list doesn't contains added film");
    }

    @Test
    @DisplayName("Should get all films")
    public void shouldGetAllFilms() {
        Film film = new Film(0, "Film name", "Film description", LocalDate.now(), 1200000);
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getAllFilms();
        assertFalse(savedFilms.isEmpty(), "saved films list is empty");
    }

    private boolean containsFilm(Film film) {
        Optional<Film> savedFilmOpt = filmController.getAllFilms().stream()
                .filter(savedFilm -> savedFilm.getName().equals(film.getName()) &&
                        savedFilm.getDescription().equals(film.getDescription()) &&
                        savedFilm.getDuration() == film.getDuration() &&
                        savedFilm.getReleaseDate().equals(film.getReleaseDate()))
                .findAny();
        return savedFilmOpt.isPresent();
    }
}