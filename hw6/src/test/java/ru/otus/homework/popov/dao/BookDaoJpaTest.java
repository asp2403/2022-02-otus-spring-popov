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
import ru.otus.homework.popov.dto.BookDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @DisplayName("должен корректно создавать DTO всех книг")
    @Test
    void shouldCorrectGetDtoAll() {
        var query = em.getEntityManager().createQuery("select new ru.otus.homework.popov.dto.BookDto(b.id, b.title, a.name, g.name, count(c)) " +
                "from Book b " +
                "left join b.author a " +
                "left join b.genre g " +
                "left join b.comments c " +
                "group by b.id order by b.id", BookDto.class);
        var expected = query.getResultList();
        var actual = bookDao.getDtoAll();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    @DisplayName("должен корректно выдавать DTO книги")
    @Test
    void shouldCorrectGetDtoById() {
        final var id = 1L;
        var query = em.getEntityManager().createQuery("select new ru.otus.homework.popov.dto.BookDto(b.id, b.title, a.name, g.name, count(c)) " +
                "from Book b " +
                "left join b.author a " +
                "left join b.genre g " +
                "left join b.comments c " +
                "where b.id = :id_book group by b.id", BookDto.class);
        query.setParameter("id_book", id);
        var expected = query.getSingleResult();
        var actual = bookDao.getDtoById(id);
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    @DisplayName("должен корректно выдавать книгу по ИД с деталями")
    @Test
    void getWithDetailsById() {
        final var id = 1L;
        var expectedBook = em.find(Book.class, id);
        var actualBook = bookDao.getWithDetailsById(id);
        assertThat(expectedBook).usingRecursiveComparison().isEqualTo(actualBook);
    }
}