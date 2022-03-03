package ru.otus.homework.popov.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("должен корректно создаваться конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        var user = new User("Vasya");
        assertAll(
                () -> assertEquals("Vasya", user.getName()),
                () -> assertEquals(0, user.getScore())
        );
    }

    @DisplayName("должен корректно увеличивать результат")
    @Test
    void shouldCorrectAddScore() {
        var user = new User("Vasya");
        var newScore = user.addScore();
        assertEquals(1, newScore);
    }

    @DisplayName("должен корректно сбрасывать результат")
    @Test
    void shouldCorrectResetScore() {
        var user = new User("Vasya");
        user.addScore();
        var score = user.resetScore();
        assertEquals(0, score);
    }

}