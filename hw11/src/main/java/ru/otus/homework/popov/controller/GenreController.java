package ru.otus.homework.popov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.repository.GenreRepository;

@RestController
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/api/genres")
    public Flux<Genre> getGenres() {
        return genreRepository.findAll();
    }


}
