package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.popov.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitle(String Title);
}
