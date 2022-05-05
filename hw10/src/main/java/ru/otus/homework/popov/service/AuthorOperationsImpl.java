package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorOperationsImpl implements AuthorOperations {
    private final AuthorRepository authorRepository;

    public AuthorOperationsImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
