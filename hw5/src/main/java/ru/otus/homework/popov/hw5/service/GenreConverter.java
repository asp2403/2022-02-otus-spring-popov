package ru.otus.homework.popov.hw5.service;

import ru.otus.homework.popov.hw5.domain.Author;
import ru.otus.homework.popov.hw5.domain.Genre;

public interface GenreConverter {
    String convertToString(Genre genre);
}
