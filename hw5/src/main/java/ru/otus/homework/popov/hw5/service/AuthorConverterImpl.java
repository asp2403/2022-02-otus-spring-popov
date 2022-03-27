package ru.otus.homework.popov.hw5.service;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.hw5.domain.Author;

@Component
public class AuthorConverterImpl implements AuthorConverter {
    @Override
    public String convertToString(Author author) {
        return author.getId() + ": " + author.getName();
    }
}
