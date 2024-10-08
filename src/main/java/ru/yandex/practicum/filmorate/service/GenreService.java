package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.getAllGenres();
    }

    public Genre findGenreById(int genreId) {
        return genreRepository.findGenreById(genreId).orElseThrow(() -> new NotFoundException(String.format("Genre with id %d doesn't exist", genreId)));
    }
}
