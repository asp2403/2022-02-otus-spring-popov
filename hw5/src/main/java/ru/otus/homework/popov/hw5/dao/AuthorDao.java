package ru.otus.homework.popov.hw5.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.hw5.domain.Author;

import java.util.List;


public interface AuthorDao {
    List<Author> getAll();
    Author getById(long id);
}
