package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReleaseDateValidation.class)
public @interface ReleaseDate {
    String message() default "Release date should be after 28 december 1895 y.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}