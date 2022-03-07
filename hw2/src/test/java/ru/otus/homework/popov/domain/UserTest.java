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
                () -> assertEquals("Vasya", user.getName())
        );
    }

}