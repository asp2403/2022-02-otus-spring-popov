package ru.otus.homework.popov.hw5.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.popov.hw5.dao.AuthorDao;
import ru.otus.homework.popov.hw5.dao.GenreDao;
import ru.otus.homework.popov.hw5.domain.Author;
import ru.otus.homework.popov.hw5.domain.Book;
import ru.otus.homework.popov.hw5.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BookConverterImplTest {

    @Autowired
    BookConverter bookConverter;

    @MockBean
    AuthorDao authorDao;

    @MockBean
    GenreDao genreDao;

    @Test
    void convertToString() {
        final long authorId = 1;
        final String authorName = "Author";
        final long genreId = 2;
        final String genreName = "Genre";
        final long bookId = 3;
        final String bookTitle = "Title";
        var book = new Book(bookId, bookTitle, authorId, genreId);
        var author = new Author(authorId, authorName);
        var genre = new Genre(genreId, genreName);
        given(authorDao.getById(anyLong())).willReturn(author);
        given(genreDao.getById(anyLong())).willReturn(genre);
        var s = bookConverter.convertToString(book);
        assertThat(s).contains(authorName, bookTitle, genreName);
    }
}