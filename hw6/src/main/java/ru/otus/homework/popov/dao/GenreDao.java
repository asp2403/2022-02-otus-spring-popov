package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
    Genre getById(long id);
}
