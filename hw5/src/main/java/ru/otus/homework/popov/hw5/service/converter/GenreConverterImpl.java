package ru.otus.homework.popov.hw5.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.hw5.domain.Genre;

@Component
public class GenreConverterImpl implements GenreConverter {
    @Override
    public String convertToString(Genre genre) {
        return genre.getId() + ": " + genre.getName();
    }
}
