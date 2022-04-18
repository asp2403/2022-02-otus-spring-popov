package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.domain.Author;

@Component
public class AuthorConverterImpl implements AuthorConverter {
    @Override
    public String convertToString(Author author) {
        return author.getId() + ": " + author.getName();
    }
}
