package ru.otus.homework.popov.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;
import ru.otus.homework.popov.service.converter.BookConverter;
import ru.otus.homework.popov.service.converter.CommentConverter;
import ru.otus.homework.popov.service.localization.MessageService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(CommentCommandsImpl.class)
class CommentCommandsImplTest {
    @Autowired
    private BookRepository bookRepository;
    @MockBean
    private CommentConverter commentConverter;
    @MockBean
    private MessageService messageService;
    @MockBean
    private BookConverter bookConverter;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentCommands commentCommands;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setup() {
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        var author = authorRepository.findById("1");
        var genre = genreRepository.findById("1");
        var book = new Book("1", "Title", author.get(), genre.get());
        bookRepository.save(book);
        var comment = new Comment("1", "Comment");
        book.addComment(comment);
        commentRepository.save(comment);
        bookRepository.save(book);
    }

    @DisplayName("должен корректно добавлять комментарий")
    @Test
    void shouldCorrectAddComment() {
        var newCommentText = "NewComment";
        var bookId = "1";
        commentCommands.addComment(bookId, newCommentText);
        var actualComments = commentRepository.findByBookId(bookId);
        assertThat(actualComments.size()).isEqualTo(2);
        var actualComment = actualComments.get(1);
        assertThat(actualComment.getText()).isEqualTo(newCommentText);
        var book = bookRepository.findById(bookId);
        assertThat(book).isPresent();
        book.ifPresent(
                b -> assertThat(b.getCommentCount()).isEqualTo(2)
        );
    }

    @DisplayName("должен корректно обновлять комментарий")
    @Test
    void shouldCorrectUpdateComment() {
        var newCommentText = "NewComment";
        var commentId = "1";
        commentCommands.updateComment(commentId, newCommentText);
        var actualComment = commentRepository.findById(commentId);
        assertThat(actualComment).isPresent();
        actualComment.ifPresent(
                c -> assertThat(c.getText()).isEqualTo(newCommentText)
        );
    }

    @DisplayName("должен корректно удалять комментарий")
    @Test
    void shouldCorrectDeleteComment() {
        var commentId = "1";
        var bookId = "1";
        commentCommands.deleteComment(commentId);
        var comment = commentRepository.findById(commentId);
        assertThat(comment).isEmpty();
        var book = bookRepository.findById(bookId);
        assertThat(book).isPresent();
        book.ifPresent(
                b -> assertThat(b.getCommentCount()).isEqualTo(0)
        );
    }
}