package ru.otus.homework.popov.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestingResultTest {


    private User user;
    private TestingResult testingResult;

    @BeforeEach
    void setUp() {
        user = new User("Vasya", "Pupkin");
        testingResult = new TestingResult(user);
    }

    @DisplayName("должен корректно создаваться конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        assertAll(
                () -> assertEquals(user, testingResult.getUser()),
                () -> assertEquals(0, testingResult.getScore()),
                () -> assertFalse(testingResult.isAborted())
        );
    }

    @DisplayName("должен корректно применять результат тестирования")
    @Test
    void applyAnswer() {
        testingResult.applyAnswer(true);
        assertEquals(1, testingResult.getScore());
        testingResult.applyAnswer(false);
        assertEquals(1, testingResult.getScore());
    }
}