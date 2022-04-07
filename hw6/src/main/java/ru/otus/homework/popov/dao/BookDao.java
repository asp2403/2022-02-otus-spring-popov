package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    Book save(Book book);
    void deleteById(long id);
    Book getById(long id);
}
