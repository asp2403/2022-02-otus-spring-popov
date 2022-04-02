package ru.otus.homework.popov.hw5.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.hw5.domain.Book;

@Component
public class BookConverterImpl implements BookConverter {

    @Override
    public String convertToString(Book book) {
        return book.getId() + ": " + book.getAuthor().getName() + " \"" + book.getTitle() + "\" (" + book.getGenre().getName() + ")";
    }
}
