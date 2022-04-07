package ru.otus.homework.popov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Genre;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("должен корректно выдавать список книг")
    void shouldCorrectGetAll() {
        var expected = em.getEntityManager().createQuery("select b from Book b", Book.class).getResultList();
        var actual = bookDao.getAll();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("должен корректно добавлять книгу")
    void shouldCorrectInsertBook() {
        var newBook = new Book(0, "Title3", new Author(1, "Author1"), new Genre(2, "Genre2"));
        bookDao.save(newBook);
        var actual = bookDao.getAll();
        assertThat(actual).filteredOn(
                        book ->
                                book.getTitle() == "Title3" &&
                                        book.getAuthor().getId() == 1 &&
                                        book.getGenre().getId() == 2)
                .isNotEmpty();
    }

    @Test
    @DisplayName("должен корректно удалять книгу по ИД")
    void shouldCorrectDeleteById() {
        var book = em.find(Book.class, 1L);
        assertThat(book).isNotNull();
        bookDao.deleteById(1);
        em.detach(book);
        book = em.find(Book.class, 1L);
        assertThat(book).isNull();
    }

    @Test
    @DisplayName("должен корректно обновлять книгу")
    void shouldCorrectUpdateBook() {
        var expected = em.find(Book.class, 1L);
        expected.setTitle("NEW TITLE");
        bookDao.save(expected);
        var actual = em.find(Book.class, 1L);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен корректно получать книгу по ИД")
    void shouldCorrectGetById() {
        var expected = em.find(Book.class, 2L);
        var actual = bookDao.getById(2);
        assertEquals(expected, actual);
    }
}