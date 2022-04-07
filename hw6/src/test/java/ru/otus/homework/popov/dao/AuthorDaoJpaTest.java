package ru.otus.homework.popov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.popov.domain.Author;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("должен корректно выдавать список авторов")
    void shouldCorrectGetAll() {
        var query = em.getEntityManager().createQuery("select a from Author a", Author.class);
        var expected = query.getResultList();
        var actual = authorDao.getAll();
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("должен корректно получать автора по ИД")
    void shouldCorrectGetById() {
        var expected = em.find(Author.class, 1L);
        var actual = authorDao.getById(1);
        assertEquals(expected, actual);
    }
}