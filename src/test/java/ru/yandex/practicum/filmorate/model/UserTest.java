package ru.yandex.practicum.filmorate.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserTest")
public class UserTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Should validate user with empty email")
    public void shouldValidateUserWithEmptyEmail() {
        User user = new User(0, "", "user", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate user with null email")
    public void shouldValidateUserWithNullEmail() {
        User user = new User(0, null, "user", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate user with invalid email")
    public void shouldValidateUserWithInvalidEmail() {
        User user = new User(0, "vasya", "user", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate user with empty login")
    public void shouldValidateUserWithEmptyLogin() {
        User user = new User(0, "user@gmail.com", "", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate user with null login")
    public void shouldValidateUserWithNullLogin() {
        User user = new User(0, "user@gmail.com", null, "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate user with invalid login")
    public void shouldValidateUserWithInvalidLogin() {
        User user = new User(0, "user@gmail.com", "lo gin", "username", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should not validate user with empty name")
    public void shouldValidateUserWithEmptyName() {
        User user = new User(0, "user@gmail.com", "login", "", LocalDate.of(1995, 10, 11), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Should validate birthday in future")
    public void shouldValidateBirthdayInFuture() {
        User user = new User(0, "user@gmail.com", "login", "", LocalDate.now().plusYears(1), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Should not validate correct birthday")
    public void shouldNotValidateCorrectBirthday() {
        User user = new User(0, "user@gmail.com", "login", "", LocalDate.now().minusYears(1), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
}