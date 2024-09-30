package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
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
}