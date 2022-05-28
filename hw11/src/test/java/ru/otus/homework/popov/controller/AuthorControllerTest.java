package ru.otus.homework.popov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.repository.AuthorRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebFluxTest
@ContextConfiguration(classes = AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AuthorRepository authorRepository;

    @DisplayName("должен корректно выводить список авторов")
    @Test
    void shouldCorrectGetAuthors() throws JsonProcessingException {
        var authorName1 = "Author1";
        var author1 = new Author("1", authorName1);
        var authorName2 = "Author2";
        var author2 = new Author("2", authorName2);
        var authors = Arrays.asList(author1, author2);
        var fluxAuthors = Flux.fromIterable(authors);
        var expected = mapper.writeValueAsString(authors);
        given(authorRepository.findAll()).willReturn(fluxAuthors);
        webClient.get().uri("/api/authors")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }
}