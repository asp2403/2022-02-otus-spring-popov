package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.popov.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
