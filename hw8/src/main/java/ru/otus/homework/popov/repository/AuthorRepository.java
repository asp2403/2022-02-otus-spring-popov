package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.popov.domain.Author;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
