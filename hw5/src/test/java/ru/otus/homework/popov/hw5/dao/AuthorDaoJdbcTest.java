package ru.otus.homework.popov.hw5.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.popov.hw5.domain.Author;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("должен корректно выдавать список авторов")
    void shouldCorrectGetAll() {
        var expected = Arrays.asList(
                new Author(1, "Author1"),
                new Author(2, "Author2"),
                new Author(3, "Author3"));
        var actual = authorDao.getAll();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("должен корректно получать автора по ИД")
    void shouldCorrectGetById() {
        var expected = new Author(1, "Author1");
        var actual = authorDao.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен бросать EmptyResultDataAccessException при неправильном ИД")
    void shouldThrowEmptyResultDataAccessExceptionOnWrongId() {
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> authorDao.getById(100));
    }
}