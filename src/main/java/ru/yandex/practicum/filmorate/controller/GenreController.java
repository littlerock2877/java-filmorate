package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> getAllGenres() {
        log.info("Getting all genres - Started");
        List<Genre> allGenres = genreService.getAllGenres();
        log.info("Getting all genres - {}", allGenres.toString());
        return allGenres;
    }

    @GetMapping("/{genreId}")
    public Genre findGenreById(@PathVariable int genreId) {
        log.info("Getting genre with id {} - Started", genreId);
        Genre genre = genreService.findGenreById(genreId);
        log.info("Getting genre with id {} - Genre = {}", genreId, genre);
        return genre;
    }
}
