package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private int id;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotBlank(message = "Login should not be null and should contains at least one symbol")
    @Pattern(regexp = "^\\S*$", message = "Login should not contains space symbols")
    private String login;

    private String name;

    @PastOrPresent(message = "Birthday couldn't be in the future")
    private LocalDate birthday;
}