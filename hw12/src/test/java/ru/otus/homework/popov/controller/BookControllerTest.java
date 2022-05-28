package ru.otus.homework.popov.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.service.AuthorOperations;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;
import ru.otus.homework.popov.service.GenreOperations;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookController.class)
@ContextConfiguration(classes = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookOperations bookOperations;

    @MockBean
    private AuthorOperations authorOperations;

    @MockBean
    private GenreOperations genreOperations;

    @MockBean
    private CommentOperations commentOperations;

    @DisplayName("должен корректно выводить стартовую страницу")
    @Test
    void shouldCorrectOutputIndexPage() throws Exception {
        var authorName1 = "Author1";
        var author1 = new Author("1", authorName1);
        var genreName1 = "Genre1";
        var genre1 = new Genre("1", genreName1);
        var authorName2 = "Author2";
        var author2 = new Author("2", authorName2);
        var genreName2 = "Genre2";
        var genre2 = new Genre("2", genreName2);
        var title1 = "Title1";
        var title2 = "Title2";
        var book1 = new Book("1", title1, author1, genre1);
        var book2 = new Book("2", title2, author2, genre2);
        var books = Arrays.asList(book1, book2);
        given(bookOperations.findAll()).willReturn(books);

        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(authorName1)))
                .andExpect(content().string(containsString(genreName1)))
                .andExpect(content().string(containsString(title1)))
                .andExpect(content().string(containsString(authorName2)))
                .andExpect(content().string(containsString(genreName2)))
                .andExpect(content().string(containsString(title2)));
    }

    @DisplayName("должен корректно выводить страницу создания книги")
    @Test
    void shouldCorrectOutputAddBookPage() throws Exception {
        var authorName1 = "Author1";
        var authorId1 = "authorId1";
        var authorName2 = "Author2";
        var authorId2 = "authorId2";
        var genreName1 = "Genre1";
        var genreId1 = "genreId1";
        var genreName2 = "Genre2";
        var genreId2 = "genreId2";
        var authors = Arrays.asList(new Author(authorId1, authorName1), new Author(authorId2, authorName2));
        var genres = Arrays.asList(new Genre(genreId1, genreName1), new Genre(genreId2, genreName2));
        given(authorOperations.findAll()).willReturn(authors);
        given(genreOperations.findAll()).willReturn(genres);
        mvc.perform(MockMvcRequestBuilders.get("/add-book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(authorId1)))
                .andExpect(content().string(containsString(authorName1)))
                .andExpect(content().string(containsString(authorId2)))
                .andExpect(content().string(containsString(authorName2)))
                .andExpect(content().string(containsString(genreId1)))
                .andExpect(content().string(containsString(genreName1)))
                .andExpect(content().string(containsString(genreId2)))
                .andExpect(content().string(containsString(genreName2)));
    }

    @DisplayName("должен корректно выводить страницу редактирования книги")
    @Test
    void shouldCorrectOutputBookEditPage() throws Exception {
        var authorName1 = "Author1";
        var authorId1 = "1";
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName1 = "Genre1";
        var genreId1 = "1";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var authors = Arrays.asList(new Author(authorId1, authorName1), new Author(authorId2, authorName2));
        var genres = Arrays.asList(new Genre(genreId1, genreName1), new Genre(genreId2, genreName2));
        var bookTitle = "Title";
        var bookId = "1";
        var book = new Book(bookId, bookTitle, new Author(authorId1, authorName1), new Genre(genreId1, genreName1));
        given(authorOperations.findAll()).willReturn(authors);
        given(genreOperations.findAll()).willReturn(genres);
        given(bookOperations.findById(eq(bookId))).willReturn(Optional.of(book));
        mvc.perform(MockMvcRequestBuilders.get("/edit-book").param("id", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookTitle)));
    }

    @DisplayName("должен корректно сохранять книгу")
    @Test
    void shouldCorrectSaveBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/save-book")
                .param("mode", "add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("id=1&author.id=1&genre.id=1&title=NewBook"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("должен корректно показывать информацию о книге")
    @Test
    void shouldCorrectOutputBookDetails() throws Exception {
        var authorName1 = "Author1";
        var authorId1 = "1";
        var genreName1 = "Genre1";
        var genreId1 = "1";
        var bookTitle = "Title";
        var bookId = "1";
        var book = new Book(bookId, bookTitle, new Author(authorId1, authorName1), new Genre(genreId1, genreName1));
        var comment1 = "Comment1";
        var comment2 = "Comment2";
        var comments = Arrays.asList(new Comment("1", comment1), new Comment("2", comment2));
        given(bookOperations.findById(eq(bookId))).willReturn(Optional.of(book));
        given(commentOperations.findByBookId(eq(bookId))).willReturn(comments);
        mvc.perform(MockMvcRequestBuilders.get("/book-details").param("id", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(authorName1)))
                .andExpect(content().string(containsString(genreName1)))
                .andExpect(content().string(containsString(bookTitle)))
                .andExpect(content().string(containsString(comment1)))
                .andExpect(content().string(containsString(comment2)));
    }

    @DisplayName("должен показывать страницу подтверждения удаления книги")
    @Test
    void shouldOutputDelBookConfirmationPage() throws Exception {
        var bookTitle = "Title";
        var bookId = "1";
        given(bookOperations.findTitleById(eq(bookId))).willReturn(bookTitle);
        mvc.perform(MockMvcRequestBuilders.get("/del-book").param("id", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(bookTitle)));
    }

    @DisplayName("должен корректно удалять книгу")
    @Test
    void shouldCorrectDelBook() throws Exception {

        mvc.perform(MockMvcRequestBuilders.post("/del-book").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }
}