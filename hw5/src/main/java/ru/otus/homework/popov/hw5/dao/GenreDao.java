package ru.otus.homework.popov.hw5.dao;

import ru.otus.homework.popov.hw5.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
    Genre getById(long id);
}
