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
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.security.AuthenticationProvider;
import ru.otus.homework.popov.security.SecurityConfiguration;
import ru.otus.homework.popov.service.AuthorOperations;
import ru.otus.homework.popov.service.GenreOperations;
import ru.otus.homework.popov.service.UserService;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@ContextConfiguration(classes = {GenreController.class, SecurityConfiguration.class})
class GenreControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private GenreOperations genreOperations;

    @DisplayName("должен корректно выводить список жанров")
    @Test
    void shouldCorrectGetGenres() throws Exception {
        var genre1 = new Genre("1", "Genre1");
        var genre2 = new Genre("2", "Genre2");
        var genres = Arrays.asList(genre1, genre2);
        var expected = mapper.writeValueAsString(genres);

        given(genreOperations.findAll()).willReturn(genres);

        mvc.perform(MockMvcRequestBuilders.get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}