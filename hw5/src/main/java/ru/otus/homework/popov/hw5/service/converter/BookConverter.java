package ru.otus.homework.popov.hw5.service.converter;

import ru.otus.homework.popov.hw5.domain.Author;
import ru.otus.homework.popov.hw5.domain.Book;

public interface BookConverter {
    String convertToString(Book book);
}
