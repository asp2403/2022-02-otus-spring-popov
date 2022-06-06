package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.repository.GenreRepository;

import java.util.List;

@Service
public class GenreOperationsImpl implements GenreOperations {
    private final GenreRepository genreRepository;

    public GenreOperationsImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
