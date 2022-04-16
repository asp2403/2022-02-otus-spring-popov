package ru.otus.homework.popov.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.projection.BookCommentCountProjection;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен корректно выдавать проекции всех книг с количество комментариев")
    @Test
    void shouldCorrectFindCommentCountProjectionAll() {
        var query = em.getEntityManager().createQuery("select b from Book b", Book.class);
        var books = query.getResultList();
        var expected = new ArrayList<BookCommentCountProjection>();
        books.forEach(b -> {
            var comments = b.getComments();
            var bookProjection = new BookCommentCountProjection(b, comments.size());
            expected.add(bookProjection);
        });
        var actual = bookRepository.findCommentCountProjectionAll();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);

    }

    @DisplayName("должен корректно выдавать проекцию книги с количество комментариев по ИД")
    @Test
    void shouldCorrectFindCommentCountProjectionById() {
        var id = 1L;
        var book = em.find(Book.class, id);
        var expected = new BookCommentCountProjection(book, book.getComments().size());
        var actual = bookRepository.findCommentCountProjectionById(id);
        assertThat(actual).isPresent();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual.get());
    }

    @DisplayName("должен корректно выдавать книгу с деталями по ИД")
    @Test
    void shouldCorrectFindWithDetailsById() {
        var id = 1L;
        var expected = em.find(Book.class, id);
        var actual = bookRepository.findWithDetailsById(id);
        assertThat(actual).isPresent();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual.get());
    }

    @DisplayName("должен корректно получать книгу по ИД")
    @Test
    void shouldCorrectGetBookById() {
        var title = "Title";
        var author = em.find(Author.class, 1L);
        var genre = em.find(Genre.class, 1L);
        var expected = new Book(0, title, author, genre);
        em.persistAndFlush(expected);
        var actual = bookRepository.findById(expected.getId());
        assertThat(actual).isPresent();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual.get());
    }

    @DisplayName("должен корректно сщхранять книгу")
    @Test
    void shouldCorrectSaveBook() {
        var title = "Title";
        var author = em.find(Author.class, 1L);
        var genre = em.find(Genre.class, 1L);
        var expected = new Book(0, title, author, genre);
        bookRepository.save(expected);
        var actual = em.find(Book.class, expected.getId());
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }
}