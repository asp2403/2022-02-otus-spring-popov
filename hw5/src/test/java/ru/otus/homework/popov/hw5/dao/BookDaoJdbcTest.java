package ru.otus.homework.popov.hw5.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.homework.popov.hw5.domain.Author;
import ru.otus.homework.popov.hw5.domain.Book;
import ru.otus.homework.popov.hw5.domain.Genre;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("должен корректно выдавать список книг")
    void shouldCorrectGetAll() {
        var expected = Arrays.asList(
                new Book(1, "Title1", new Author(1, "Author1"), new Genre(1, "Genre1")),
                new Book(2, "Title2", new Author(2, "Author2"), new Genre(2, "Genre2"))
        );
        var actual = bookDao.getAll();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("должен корректно добавлять книгу")
    void shouldCorrectInsertBook() {
        var newBook = new Book(0, "Title3", new Author(1, "Author1"), new Genre(2, "Genre2"));
        bookDao.insert(newBook);
        var actual = bookDao.getAll();
        assertThat(actual).filteredOn(
                        book ->
                                book.getTitle() == "Title3" &&
                                book.getAuthor().getId() == 1 &&
                                book.getGenre().getId() == 2)
                .isNotEmpty();
    }

    @Test
    @DisplayName("должен бросать DataIntegrityViolationException при добавлении книги с неправильным ИД автора")
    void shouldThrowDataIntegrityViolationExceptionWhenInsertBookWithWrongAuthorId() {
        var book = new Book(3, "Title3", new Author(100, "Author100"), new Genre(2, "Genre2"));
        assertThatExceptionOfType(DataIntegrityViolationException.class).
                isThrownBy(() -> bookDao.insert(book));
    }

    @Test
    @DisplayName("должен бросать DataIntegrityViolationException при добавлении книги с неправильным ИД жанра")
    void shouldThrowDataIntegrityViolationExceptionWhenInsertBookWithWrongGenreId() {
        var book = new Book(3, "Title3", new Author(1, "Author1"), new Genre(200, "Genre200"));
        assertThatExceptionOfType(DataIntegrityViolationException.class).
                isThrownBy(() -> bookDao.insert(book));
    }

    @Test
    @DisplayName("должен корректно удалять книгу по ИД")
    void shouldCorrectDeleteById() {
        var expected = Arrays.asList(
                new Book(2, "Title2", new Author(2, "Author2"), new Genre(2, "Genre2"))
        );
        bookDao.deleteById(1);
        var actual = bookDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен корректно обновлять книгу")
    void shouldCorrectUpdateBook() {
        var expected = new Book(2, "Title2 NEW", new Author(1, "Author1"), new Genre(1, "Genre1"));
        bookDao.update(expected);
        var actual = bookDao.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен бросать DataIntegrityViolationException при обновлении книги с неправильным ИД автора")
    void shouldThrowDataIntegrityViolationExceptionWhenUpdateBookWithWrongAuthorId() {
        var book = new Book(2, "Title3", new Author(100, "Author100"), new Genre(2, "Genre2"));
        assertThatExceptionOfType(DataIntegrityViolationException.class).
                isThrownBy(() -> bookDao.update(book));
    }

    @Test
    @DisplayName("должен бросать DataIntegrityViolationException при обновлении книги с неправильным ИД жанра")
    void shouldThrowDataIntegrityViolationExceptionWhenUpdateBookWithWrongGenreId() {
        var book = new Book(2, "Title3", new Author(1, "Author1"), new Genre(200, "Genre200"));
        assertThatExceptionOfType(DataIntegrityViolationException.class).
                isThrownBy(() -> bookDao.update(book));
    }

    @Test
    @DisplayName("должен корректно получать книгу по ИД")
    void shouldCorrectGetById() {
        var expected = new Book(1, "Title1", new Author(1, "Author1"), new Genre(1, "Genre1"));
        var actual = bookDao.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен бросать EmptyResultDataAccessException при получении книги по неправильному ИД")
    void shouldThrowEmptyResultDataAccessExceptionOnWrongId() {
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> bookDao.getById(100));
    }
}