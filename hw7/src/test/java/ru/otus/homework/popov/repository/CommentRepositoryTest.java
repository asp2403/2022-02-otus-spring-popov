package ru.otus.homework.popov.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен корректно получать комментарий по ИД")
    @Test
    void shouldCorrectGetComment() {
        var book = em.find(Book.class, 1L);
        var expected = new Comment(0, "Text", book);
        em.persistAndFlush(expected);
        var actual = commentRepository.findById(expected.getId());
        assertThat(actual).isPresent();
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual.get());
    }

    @DisplayName("должен корректно сохранять комментарий")
    @Test
    void shouldCorrectSaveComment() {
        var book = em.find(Book.class, 1L);
        var expected = new Comment(0, "Text", book);
        commentRepository.save(expected);
        var actual = em.find(Comment.class, expected.getId());
        assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }

}