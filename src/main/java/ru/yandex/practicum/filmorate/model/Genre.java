package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Genre {
    private int id;

    @NotBlank(message = "Genre name could not be empty")
    private String name;
}
