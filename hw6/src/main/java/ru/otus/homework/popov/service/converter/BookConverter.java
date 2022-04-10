package ru.otus.homework.popov.service.converter;


import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;

import java.util.List;

public interface BookConverter {
    String convertToString(Book book);
    String convertToString(Book book, long commentCount);
}
