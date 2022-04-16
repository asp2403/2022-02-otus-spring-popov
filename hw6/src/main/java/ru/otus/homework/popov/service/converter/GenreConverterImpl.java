package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.domain.Genre;

@Component
public class GenreConverterImpl implements GenreConverter {
    @Override
    public String convertToString(Genre genre) {
        return genre.getId() + ": " + genre.getName();
    }
}
