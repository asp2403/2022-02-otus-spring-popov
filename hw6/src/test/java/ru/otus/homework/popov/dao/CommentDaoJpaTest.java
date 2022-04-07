package ru.otus.homework.popov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentDao commentDao;

    @DisplayName("должен корректно получать комментарий по Id")
    @Test
    void getById() {
        var expected = em.find(Comment.class, 1L);
        var actual = commentDao.getById(1L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("должен корректно сохранять комментарий")
    @Test
    void shouldCorrectSaveComment() {
        var expected = em.find(Comment.class, 3L);
        expected.setText("NEW TEXT");
        commentDao.save(expected);
        var actual = em.find(Comment.class, 3L);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @DisplayName("должен корректно удалять комментарий")
    @Test
    void deleteById() {
        var comment = em.find(Comment.class, 3L);
        assertThat(comment).isNotNull();
        commentDao.deleteById(3L);
        em.detach(comment);
        comment = em.find(Comment.class, 3L);
        assertThat(comment).isNull();
        
    }
}