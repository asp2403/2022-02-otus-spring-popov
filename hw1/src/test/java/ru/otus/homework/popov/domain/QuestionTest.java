package ru.otus.homework.popov.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class QuestionTest {

    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        var body = "My question";
        var answers = Arrays.asList("My First Answer", "My Second Answer");
        var question = new Question(body, answers);
        assertAll(
                () -> assertEquals(body, question.getBody()),
                () -> assertTrue(question.getAnswers().equals(answers))
        );
    }

}