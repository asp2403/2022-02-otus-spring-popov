package ru.otus.homework.popov.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.*;
import ru.otus.homework.popov.security.AuthenticationProvider;
import ru.otus.homework.popov.security.SecurityConfiguration;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;
import ru.otus.homework.popov.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@ContextConfiguration(classes = {CommentController.class, SecurityConfiguration.class, AuthenticationProvider.class})
class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private CommentOperations commentOperations;

    @MockBean
    private BookOperations bookOperations;

    @Autowired
    private ObjectMapper mapper;

    private static Stream<Arguments> generateDataForAddComment() {
        return Stream.of(
                Arguments.of("anonymous", "ROLE_ANONYMOUS", status().isForbidden()),
                Arguments.of("user", "ROLE_USER", status().isOk()),
                Arguments.of("moderator", "ROLE_MODERATOR", status().isOk()),
                Arguments.of("admin", "ROLE_ADMIN",status().isOk())
        );
    }

    @DisplayName("должен корректно добавлять комментарий")
    @ParameterizedTest
    @MethodSource("generateDataForAddComment")
    void shouldCorrectAddComment(String username, String authority, ResultMatcher matcher) throws Exception {
        var user = new User();
        user.setUsername(username);
        user.setName("name_" + username);
        user.setSurname("surname_" + username);
        user.setRoles(List.of(new SimpleGrantedAuthority(authority)));
        given(userService.findByToken(any())).willReturn(Optional.of(user));
        var book = new Book("1", "Title", new Author("1", "Author"), new Genre("1", "Genre"));
        var commentDto = new CommentDto(null, "CommentText", "CommentAuthor", "1");
        given(bookOperations.findById(any())).willReturn(Optional.of(book));
        var comment = new Comment("1", "CommentText", "CommentAuthor");
        given(commentOperations.addComment(any(), any())).willReturn(comment);
        var contentBody = mapper.writeValueAsString(commentDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/comments").contentType(APPLICATION_JSON)
                        .content(contentBody).header("AUTHORIZATION", "Bearer XXXXXXXXXXXXX"))
                .andExpect(matcher);
    }

    private static Stream<Arguments> generateDataForDelComment() {
        return Stream.of(
                Arguments.of("ROLE_ANONYMOUS", status().isForbidden()),
                Arguments.of("ROLE_USER", status().isForbidden()),
                Arguments.of("ROLE_MODERATOR", status().isOk()),
                Arguments.of("ROLE_ADMIN",status().isOk())
        );
    }

    @DisplayName("должен корректно удалять комментарий")
    @ParameterizedTest
    @MethodSource("generateDataForDelComment")
    void shouldCorrectDeleteComment(String authority, ResultMatcher matcher) throws Exception {
        var user = new User();
        user.setRoles(List.of(new SimpleGrantedAuthority(authority)));
        given(userService.findByToken(any())).willReturn(Optional.of(user));
        var comment = new Comment("1", "CommentText", "CommentAuthor");
        given(commentOperations.findById(any())).willReturn(Optional.of(comment));
        mvc.perform(MockMvcRequestBuilders.delete("/api/comments/1").header("AUTHORIZATION", "Bearer XXXXXXXXXXXXX"))
                .andExpect(matcher);
    }
}