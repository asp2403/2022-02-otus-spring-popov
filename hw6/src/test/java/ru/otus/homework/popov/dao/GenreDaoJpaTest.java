package ru.otus.homework.popov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.popov.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GenreDaoJpa.class)
class GenreDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreDao genreDao;

    @DisplayName("должен корректно выводить список жанров")
    @Test
    void shouldCorrectGetAll() {
        var expected = em.getEntityManager().createQuery("select g from Genre g", Genre.class).getResultList();
        var actual = genreDao.getAll();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

    @DisplayName("должен корректно выдавать жанр по ИД")
    @Test
    void shouldCorrectGetById() {
        var expected = em.find(Genre.class, 1L);
        var actual = genreDao.getById(1);
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }
}