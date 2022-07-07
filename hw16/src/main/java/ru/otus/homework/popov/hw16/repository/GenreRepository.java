package ru.otus.homework.popov.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.homework.popov.hw16.domain.Author;
import ru.otus.homework.popov.hw16.domain.Genre;


import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);

    @Override
    @RestResource(exported = false)
    void delete(Genre genre);
}
