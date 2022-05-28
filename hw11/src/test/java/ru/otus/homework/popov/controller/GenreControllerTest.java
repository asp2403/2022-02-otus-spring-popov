package ru.otus.homework.popov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.repository.GenreRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@WebFluxTest
@ContextConfiguration(classes = GenreController.class)
class GenreControllerTest {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GenreRepository genreRepository;

    @DisplayName("должен корректно выводить список жанров")
    @Test
    void shouldCorrectGetGenres() throws JsonProcessingException {
        var genre1 = new Genre("1", "Genre1");
        var genre2 = new Genre("2", "Genre2");
        var genres = Arrays.asList(genre1, genre2);
        var fluxGenres = Flux.fromIterable(genres);
        var expected = mapper.writeValueAsString(genres);
        given(genreRepository.findAll()).willReturn(fluxGenres);
        webClient.get().uri("/api/genres")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(expected);
    }
}