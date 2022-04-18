package ru.otus.homework.popov.service.converter;

import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;

public interface BookConverter {
    String convertToString(Book book);
}
