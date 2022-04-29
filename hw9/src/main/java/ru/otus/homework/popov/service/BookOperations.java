package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookOperations {
    List<Book> findAll();
    Optional<Book> findById(String id);
    void save(BookDto bookDto);
    void delete(String id);
}
