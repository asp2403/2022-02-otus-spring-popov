package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    void insert(Book book);
    void deleteById(long id);
    void update(Book book);
    Book getById(long id);
}
