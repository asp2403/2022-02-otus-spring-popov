package ru.otus.homework.popov.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @DisplayName("должен корректно создаваться конструктором")
    @Test
    void shouldHaveCorrectConstructor() {
        var name = "Vasya";
        var surname = "Pupkin";
        var fullName = name + " " + surname;
        var user = new User("Vasya", "Pupkin");
        assertAll(
                () -> assertEquals(name, user.getName()),
                () -> assertEquals(surname, user.getSurname()),
                () -> assertEquals(fullName, user.getFullName())
        );
    }

}