package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;

    @NotBlank(message = "Film name should not be empty")
    private String name;

    @Size(max = 200, message = "Film description could maximum contains 200 symbols")
    private String description;

    @ReleaseDate
    private LocalDate releaseDate;

    @Positive(message = "Film duration should be positive")
    private long duration;

    private LinkedHashSet<Genre> genres;

    @NotNull
    private Mpa mpa;

    @JsonIgnore
    private Set<Integer> likedUsersIds;

    public void addGenre(Genre genre) {
        if (genres == null) {
            genres = new LinkedHashSet<>();
        }
        genres.add(genre);
    }
}