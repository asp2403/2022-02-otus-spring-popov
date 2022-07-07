package ru.otus.homework.popov.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.homework.popov.hw16.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByName(String name);

    @Override
    @RestResource(exported = false)
    void delete(Author author);
}
