package ru.otus.homework.popov.hw5.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.popov.hw5.domain.Author;
import ru.otus.homework.popov.hw5.domain.Genre;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("должен корректно выдавать список жанров")
    void shouldCorrectGetAll() {
        var expected = Arrays.asList(
                new Genre(1, "Genre1"),
                new Genre(2, "Genre2"));
        var actual = genreDao.getAll();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("должен корректно получать жанр по ИД")
    void shouldCorrectGetById() {
        var expected = new Genre(1, "Genre1");
        var actual = genreDao.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен бросать EmptyResultDataAccessException при неправильном ИД")
    void shouldThrowEmptyResultDataAccessExceptionOnWrongId() {
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> genreDao.getById(100));
    }

}