package ru.otus.homework.popov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebFluxTest
@ContextConfiguration(classes = BookController.class)
class BookControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("должен корректно выводить список книг")
    @Test
    void shouldCorrectGetBooks() throws JsonProcessingException {
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
        var fluxBooks = Flux.fromIterable(books);
        var expected = mapper.writeValueAsString(books);
        given(bookRepository.findAll()).willReturn(fluxBooks);
        webClient.get().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }

    @DisplayName("должен корректно выводить информацию о книге")
    @Test
    void shouldCorrectGetBook() throws JsonProcessingException {
        var authorName1 = "Author1";
        var authorId1 = "1";
        var genreName1 = "Genre1";
        var genreId1 = "1";
        var bookTitle = "Title";
        var bookId = "1";
        var book = new Book(bookId, bookTitle, new Author(authorId1, authorName1), new Genre(genreId1, genreName1));
        var monoBook = Mono.just(book);
        var expected = mapper.writeValueAsString(book);
        given(bookRepository.findById(eq("1"))).willReturn(monoBook);
        webClient.get().uri("/api/books/1")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }

    @DisplayName("должен выдавать NotFound при запросе книги по несуществующему ИД")
    @Test
    void shouldThrowNotFoundWhenBookIsNotExist() {
        given(bookRepository.findById(eq("100"))).willReturn(Mono.empty());
        webClient.get().uri("/api/books/100")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @DisplayName("должен корректно выдавать комментарии к книге")
    @Test
    void shouldCorrectGetComments() throws JsonProcessingException {
        var comment1 = "Comment1";
        var comment2 = "Comment2";
        var comments = Arrays.asList(new Comment("1", comment1), new Comment("2", comment2));
        var commentDtos = Arrays.asList(new CommentDto("1", comment1), new CommentDto("2", comment2));
        var fluxComments = Flux.fromIterable(comments);
        var expected = mapper.writeValueAsString(commentDtos);

        given(commentRepository.findByBookId(eq("1"))).willReturn(fluxComments);

        webClient.get().uri("/api/books/1/comments")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }

    @DisplayName("должен корректно обновлять книгу")
    @Test
    void shouldCorrectUpdateBook()  {
        var authorName1 = "Author1";
        var authorId1 = "1";
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName1 = "Genre1";
        var genreId1 = "1";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var bookId = "1";
        var title = "Title1";
        var newTitle = "Title2";
        var oldBook = new Book(bookId, title, new Author(authorId1, authorName1), new Genre(genreId1, genreName1));
        var newBook = new Book(bookId, newTitle, new Author(authorId2, authorName2), new Genre(genreId2, genreName2));
        var monoOldBook = Mono.just(oldBook);
        var monoNewNook = Mono.just(newBook);
        given(bookRepository.findById("1")).willReturn(monoOldBook);
        given(bookRepository.save(eq(newBook))).willReturn(monoNewNook);

        webClient.put().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("должен выдавать NotFound, если книги, которую пытаюися обновить, не существует")
    @Test
    void shouldThrowNotFoundIfBookIsNotExistWhenUpdate() {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var bookId = "1";
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);

        given(bookRepository.findById("1")).willReturn(Mono.empty());

        webClient.put().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isNotFound();

    }

    @DisplayName("должен корректно создавать книгу")
    @Test
    void shouldCorrectCreateBook() throws JsonProcessingException {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        String bookId = null;
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);
        var bookCreated = new Book(bookId, newTitle, newAuthor, newGenre);
        var newId = "newid";
        bookCreated.setId(newId);
        var expected = mapper.writeValueAsString(bookCreated);

        given(bookRepository.save(eq(newBook))).willReturn(Mono.just(bookCreated));
        webClient.post().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }

    @DisplayName("должен выдавать BadRequest, если новая книга имеет ИД, отличный от null")
    @Test
    void shouldThrowBadRequestIfBookHasNotNullId() {
        var authorName2 = "Author2";
        var authorId2 = "2";
        var genreName2 = "Genre2";
        var genreId2 = "2";
        var bookId = "id";
        var newTitle = "Title2";
        var newAuthor = new Author(authorId2, authorName2);
        var newGenre = new Genre(genreId2, genreName2);
        var newBook = new Book(bookId, newTitle, newAuthor, newGenre);

        webClient.post().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @DisplayName("должен корректно удалять книгу")
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        var bookId = "1";
        given(commentRepository.deleteByBookId(eq(bookId))).willReturn(Mono.empty());
        given(bookRepository.deleteById(bookId)).willReturn(Mono.empty());
        webClient.delete().uri("/api/books/1")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk();
    }
}