package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(BookOperationsImpl.class)
class BookOperationsImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookOperations bookOperations;

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

    @DisplayName("должен корректно добавлять книгу")
    @Test
    void shouldCorrectInsertBook() {
        var title = "NewTitle";
        var authorId = "1";
        var genreId = "1";
        var books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(0);
        var bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setAuthor(new BookDto.Author(authorId));
        bookDto.setGenre(new BookDto.Genre(genreId));
        bookOperations.save(bookDto);
        books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(1);
        var book = books.get(0);
        assertAll(
                () -> assertEquals(title, book.getTitle()),
                () -> assertEquals(authorId, book.getAuthor().getId()),
                () -> assertEquals(genreId, book.getGenre().getId())
        );
    }

    @DisplayName("должен корректно обновлять книгу")
    @Test
    void shouldCorrectUpdateBook() {
        var bookId = "1";
        var newAuthorId = "2";
        var newGenreId = "3";
        var newTitle = "NewTitle";
        var bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle(newTitle);
        bookDto.setAuthor(new BookDto.Author(newAuthorId));
        bookDto.setGenre(new BookDto.Genre(newGenreId));
        bookOperations.save(bookDto);
        var book = bookRepository.findById(bookId);
        assertThat(book).isPresent();
        book.ifPresent(
                b -> assertAll(
                        () -> assertEquals(newTitle, b.getTitle()),
                        () -> assertEquals(newAuthorId, b.getAuthor().getId()),
                        () -> assertEquals(newGenreId, b.getGenre().getId())
                )
        );
    }

    @DisplayName("должен удалять комментарии при удалении книги")
    @Test
    void shouldDeleteCommentsAfterDeleteBook() {
        bookOperations.delete("1");
        var comments = commentRepository.findByBookId("1");
        assertThat(comments.size()).isEqualTo(0);
    }

    @DisplayName("должен корректно возвращать название книги")
    @Test
    void shouldCorrectReturnTitle() {
        var expected = "Title";
        var actual = bookOperations.findTitleById("1");
        assertThat(actual).isEqualTo(expected);
    }

}