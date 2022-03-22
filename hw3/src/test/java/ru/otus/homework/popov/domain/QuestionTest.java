package ru.otus.homework.popov.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class QuestionTest {

    @DisplayName("должен корректно создаваться конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        var body = "My question";
        var answers = Arrays.asList(new Answer("My First Answer", true), new Answer("My Second Answer", false));
        var question = new Question(0, body, answers);
        assertAll(
                () -> assertEquals(0, question.getIndex()),
                () -> assertEquals(body, question.getBody()),
                () -> assertEquals(answers, question.getAnswers())
        );
    }

}