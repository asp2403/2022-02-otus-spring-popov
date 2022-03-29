package ru.otus.homework.popov.hw5.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.popov.hw5.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorConverterImplTest {

    @Autowired
    AuthorConverter authorConverter;

    @Test
    @DisplayName("должен корректно конвертировать в строку")
    void shouldCorrectConvertToString() {
        final long id = 1;
        final String name = "Author";
        var author = new Author(id, name);
        var s = authorConverter.convertToString(author);
        assertThat(s).contains(Long.toString(id), name);
    }
}