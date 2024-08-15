package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@RestController
@RequestMapping("films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    private int counter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@RequestBody @Valid Film film) {
        log.info("Adding film {} - Started", film);
        int id = generateId();
        film.setId(id);
        films.put(id, film);
        log.info("Adding film {} - Finished", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("Updating film {} - Started", film);
        int id = film.getId();
        if (!films.containsKey(id)) {
            throw new IllegalArgumentException("Films doesn't contains film with id" + id);
        }
        films.put(id, film);
        log.info("Updating film {} - Finished", film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private int generateId() {
        return ++counter;
    }
}