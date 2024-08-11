package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.validation.ReleaseDate;

import java.time.LocalDate;

@Data
@AllArgsConstructor
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
}