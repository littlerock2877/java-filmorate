package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaRepository mpaRepository;

    public List<Mpa> getAllRatings() {
        return mpaRepository.getAllRatings();
    }

    public Mpa findMpaById(int mpaId) {
        return mpaRepository.findMpaById(mpaId).orElseThrow(() -> new NotFoundException(String.format("Rating with id %d doesn't exist", mpaId)));
    }
}
