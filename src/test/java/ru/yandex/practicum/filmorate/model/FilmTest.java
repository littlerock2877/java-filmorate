package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("FilmTest")
public class FilmTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Should validate film with empty name")
    public void shouldValidateFilmWithEmptyName() {
        Film film = new Film(0, "", "Film description", LocalDate.now(), 1200000, new LinkedHashSet<>(), new Mpa(0, "PG-13"), new HashSet<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate film with null name")
    public void shouldValidateFilmWithNullName() {
        Film film = new Film(0, null, "Film description", LocalDate.now(), 1200000, new LinkedHashSet<>(), new Mpa(0, "PG-13"), new HashSet<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate film with long description")
    public void shouldValidateFilmWithLongDescription() {
        Film film = new Film(0, "Film name", generateStringWithSize(201), LocalDate.now(), 1200000, new LinkedHashSet<>(), new Mpa(0, "PG-13"), new HashSet<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate film with negative duration")
    public void shouldValidateFilmWithNegativeDuration() {
        Film film = new Film(0, "Film name", "Film description", LocalDate.now(), -1, new LinkedHashSet<>(), new Mpa(0, "PG-13"), new HashSet<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate film with incorrect release date")
    public void shouldValidateFilmWithIncorrectReleaseDate() {
        Film film = new Film(0, "Film name", "Film description", LocalDate.of(1,1, 1), 1200000, new LinkedHashSet<>(), new Mpa(0, "PG-13"), new HashSet<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    private String generateStringWithSize(int size) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1).limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}