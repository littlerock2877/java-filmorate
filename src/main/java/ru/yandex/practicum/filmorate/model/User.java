package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonIgnore
    private Set<Integer> friendsIds;
}