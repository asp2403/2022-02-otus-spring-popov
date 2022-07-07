package ru.otus.homework.popov.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.homework.popov.hw16.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findByName(String name);
}
