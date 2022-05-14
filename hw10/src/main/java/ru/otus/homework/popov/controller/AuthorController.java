package ru.otus.homework.popov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.service.AuthorOperations;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorOperations authorOperations;

    public AuthorController(AuthorOperations authorOperations) {
        this.authorOperations = authorOperations;
    }

    @GetMapping("/api/authors")
    public List<Author> getAuthors() {
        return authorOperations.findAll();
    }
}
