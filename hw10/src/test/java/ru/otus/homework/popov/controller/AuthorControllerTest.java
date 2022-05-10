package ru.otus.homework.popov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.service.AuthorOperations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@ContextConfiguration(classes = AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorOperations authorOperations;

    @DisplayName("должен корректно выдавать список авторов")
    @Test
    void shouldCorrectGetAuthors() throws Exception {
        var authorName1 = "Author1";
        var author1 = new Author("1", authorName1);
        var authorName2 = "Author2";
        var author2 = new Author("2", authorName2);
        var authors = Arrays.asList(author1, author2);
        var expected = mapper.writeValueAsString(authors);

        given(authorOperations.findAll()).willReturn(authors);

        mvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}