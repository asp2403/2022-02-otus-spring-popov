package ru.otus.homework.popov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.exception.BadRequestException;
import ru.otus.homework.popov.exception.NotFoundException;

import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;


import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ContextConfiguration(classes = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookOperations bookOperations;

    @MockBean
    private CommentOperations commentOperations;

    @Autowired
    private ObjectMapper mapper;

    @DisplayName("должен корректно выводить список книг")
    @Test
    void shouldCorrectGetBooks() throws Exception {
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
        mvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @DisplayName("должен корректно выводить информацию о книге")
    @Test
    void shouldCorrectGetBook() throws Exception {
        var authorName1 = "Author1";
        var authorId1 = "1";
        var genreName1 = "Genre1";
        var genreId1 = "1";
        var bookTitle = "Title";
        var bookId = "1";
        var book = new Book(bookId, bookTitle, new Author(authorId1, authorName1), new Genre(genreId1, genreName1));
        given(bookOperations.findById(eq("1"))).willReturn(Optional.of(book));

        mvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));


    }

    @DisplayName("должен выдавать NotFound при запросе книги по несуществующему ИД")
    @Test
    void shouldThrowNotFoundWhenBookIsNotExist() throws Exception {
        given(bookOperations.findById(eq("100"))).willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/books/100"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("должен корректно выдавать комментарии к книге")
    @Test
    void shouldCorrectGetComments() throws Exception {
        var comment1 = "Comment1";
        var comment2 = "Comment2";
        var comments = Arrays.asList(new Comment("1", comment1, "Author1"), new Comment("2", comment2, "Author2"));
        var commentDtos = Arrays.asList(new CommentDto("1", comment1, "Author1", "1"), new CommentDto("2", comment2, "Author2", "2"));

        given(commentOperations.findByBookId(eq("1"))).willReturn(comments);

        mvc.perform(MockMvcRequestBuilders.get("/api/books/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDtos)));
    }

    @DisplayName("должен корректно обновлять книгу")
    @Test
    void shouldCorrectUpdateBook() throws Exception {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var bookId = "1";
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);
        var contentBody = mapper.writeValueAsString(newBook);

        mvc.perform(MockMvcRequestBuilders.put("/api/books").contentType(APPLICATION_JSON)
                        .content(contentBody))
                .andExpect(status().isOk());
    }

    @DisplayName("должен выдавать NotFound, если книги, которую пытаюися обновить, не существует")
    @Test
    void shouldThrowNotFoundIfBookIsNotExistWhenUpdate() throws Exception {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var bookId = "1";
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);
        var contentBody = mapper.writeValueAsString(newBook);

        doThrow(new NotFoundException()).when(bookOperations).updateBook(newBook);

        mvc.perform(MockMvcRequestBuilders.put("/api/books").contentType(APPLICATION_JSON)
                        .content(contentBody))
                .andExpect(status().isNotFound());

    }

    @DisplayName("должен корректно создавать книгу")
    @Test
    void shouldCorrectCreateBook() throws Exception {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        String bookId = null;
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);
        var contentBody = mapper.writeValueAsString(newBook);
        var bookCreated = new Book(bookId, newTitle, newAuthor, newGenre);
        var newId = "newid";
        bookCreated.setId(newId);
        var expected = mapper.writeValueAsString(bookCreated);
        given(bookOperations.createBook(eq(newBook))).willReturn(bookCreated);
        mvc.perform(MockMvcRequestBuilders.post("/api/books").contentType(APPLICATION_JSON)
                        .content(contentBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @DisplayName("должен выдавать BadRequest, если новая книга имеет ИД, отличный от null")
    @Test
    void shouldThrowBadRequestIfBookHasNotNullId() throws Exception {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var bookId = "id";
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);
        var contentBody = mapper.writeValueAsString(newBook);

        doThrow(new BadRequestException()).when(bookOperations).createBook(newBook);

        mvc.perform(MockMvcRequestBuilders.post("/api/books").contentType(APPLICATION_JSON)
                .content(contentBody))
                .andExpect(status().isBadRequest());

    }

    @DisplayName("должен корректно удалять книгу")
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
                .andExpect(status().isOk());
    }
}