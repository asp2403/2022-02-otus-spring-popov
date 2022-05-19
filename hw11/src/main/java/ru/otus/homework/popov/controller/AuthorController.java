package ru.otus.homework.popov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.repository.AuthorRepository;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/authors")
    public Flux<Author> getAuthors() {
        return authorRepository.findAll();
    }
}
