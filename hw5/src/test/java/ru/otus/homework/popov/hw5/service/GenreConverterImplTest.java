package ru.otus.homework.popov.hw5.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.popov.hw5.domain.Genre;
import ru.otus.homework.popov.hw5.service.converter.GenreConverter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GenreConverterImplTest {

    @Autowired
    private GenreConverter genreConverter;

    @Test
    @DisplayName("должен корректно конвертировать в строку")
    void convertToString() {
        final long id = 1;
        final String name = "Genre";
        var genre = new Genre(id, name);
        var s = genreConverter.convertToString(genre);
        assertThat(s).contains(Long.toString(id), name);
    }
}