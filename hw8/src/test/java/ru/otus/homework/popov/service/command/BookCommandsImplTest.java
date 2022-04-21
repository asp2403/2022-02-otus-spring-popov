package ru.otus.homework.popov.service.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;
import ru.otus.homework.popov.service.converter.BookConverter;
import ru.otus.homework.popov.service.localization.LocaleProvider;
import ru.otus.homework.popov.service.localization.LocaleProviderImpl;
import ru.otus.homework.popov.service.localization.MessageService;
import ru.otus.homework.popov.service.localization.MessageServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import(BookCommandsImpl.class)
class BookCommandsImplTest {

    @MockBean
    private MessageService messageService;

    @Autowired
    private BookRepository bookRepository;

    @MockBean
    private BookConverter bookConverter;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookCommands bookCommands;

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
        bookCommands.insertBook(title, authorId, genreId);
        books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(1);
        var book = books.get(0);
        assertAll(
                () -> assertEquals(title, book.getTitle()),
                () -> assertEquals(authorId, book.getAuthor().getId()),
                () -> assertEquals(genreId, book.getGenre().getId())
        );
    }

    @DisplayName("не должен добавлять книгу, если автора не существует")
    @Test
    void shouldFailInsertBookIfAuthorNotExists() {
        var title = "NewTitle";
        var authorId = "100";
        var genreId = "1";
        var books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(0);
        bookCommands.insertBook(title, authorId, genreId);
        books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(0);
    }

    @DisplayName("не должен добавлять книгу, если жанра не существует")
    @Test
    void shouldFailInsertBookIfGenreNotExists() {
        var title = "NewTitle";
        var authorId = "1";
        var genreId = "100";
        var books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(0);
        bookCommands.insertBook(title, authorId, genreId);
        books = bookRepository.findByTitle(title);
        assertThat(books.size()).isEqualTo(0);
    }

    @DisplayName("должен корректно обновлять книгу")
    @Test
    void shouldCorrectUpdateBook() {
        var bookId = "1";
        var newAuthorId = "2";
        var newGenreId = "3";
        var newTitle = "NewTitle";
        bookCommands.updateBook(bookId, newTitle, newAuthorId, newGenreId);
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
        bookCommands.deleteBook("1");
        var comments = commentRepository.findByBookId("1");
        assertThat(comments.size()).isEqualTo(0);
    }

}