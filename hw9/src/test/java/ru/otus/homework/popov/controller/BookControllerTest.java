package ru.otus.homework.popov.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.homework.popov.domain.Author;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Genre;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.service.AuthorOperations;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;
import ru.otus.homework.popov.service.GenreOperations;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(BookController.class)
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

    @Configuration
    public static class TestConfiguration {
    //без этого пытается грузить монгу
    }

    @DisplayName("должен корректно выводить стартовую страницу")
    @Test
    void shouldCorrectOutputIndexPage() throws Exception {
        var author1 = new Author("1", "Author1");
        var genre1 = new Genre("1", "Genre1");
        var author2 = new Author("2", "Author2");
        var genre2 = new Genre("2", "Genre2");
        var title1 = "Title1";
        var title2 = "Title2";
        var book1 = new Book("1", title1, author1, genre1);
        var book2 = new Book("2", title2, author2, genre2);
        var books = Arrays.asList(book1, book2);
        given(bookOperations.findAll()).willReturn(books);
        //почему-то не выдает книги в контроллер
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title1)));
    }

    @Test
    void addBook() {
    }

    @Test
    void editBook() {
    }

    @Test
    void saveBook() {
    }

    @Test
    void delBook() {
    }
}