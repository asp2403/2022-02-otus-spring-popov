package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.dto.BookDto;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    List<BookDto> getDtoAll();
    BookDto getDtoById(long id);
    Book save(Book book);
    void deleteById(long id);
    Book getById(long id);
    Book getWithDetailsById(long id);
}
