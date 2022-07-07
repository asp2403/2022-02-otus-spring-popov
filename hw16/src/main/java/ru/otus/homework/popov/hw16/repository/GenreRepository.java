package ru.otus.homework.popov.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.popov.hw16.domain.Genre;


import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);
}
