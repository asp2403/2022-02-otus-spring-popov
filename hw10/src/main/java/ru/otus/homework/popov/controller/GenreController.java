package ru.otus.homework.popov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.service.GenreOperations;

import java.util.List;

@RestController
public class GenreController {
    private final GenreOperations genreOperations;

    public GenreController(GenreOperations genreOperations) {
        this.genreOperations = genreOperations;
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreOperations.findAll();
    }
}
