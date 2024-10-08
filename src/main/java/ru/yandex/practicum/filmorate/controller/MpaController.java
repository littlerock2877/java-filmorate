package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("mpa")
@Slf4j
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> getAllRatings() {
        log.info("Getting all ratings - Started");
        List<Mpa> allRatings = mpaService.getAllRatings();
        log.info("Getting all ratings - {}", allRatings.toString());
        return allRatings;
    }

    @GetMapping("/{mpaId}")
    public Mpa findMpaById(@PathVariable int mpaId) {
        log.info("Getting mpa with id {} - Started", mpaId);
        Mpa mpa = mpaService.findMpaById(mpaId);
        log.info("Getting mpa with id {} - Mpa = {}", mpaId, mpa);
        return mpa;
    }
}
