package ru.otus.homework.popov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuestionConverterImplTest {

    @DisplayName("должен корректно конвертировать в строку")
    @Test
    void shouldCorrectConvertQuestionToString() {
        var question = new Question("Test", Arrays.asList(new Answer("Answer 1", false), new Answer("Answer 2", true)));
        var converter = new QuestionConverterImpl();
        assertEquals("1. Test\n   a. Answer 1\n   b. Answer 2\n", converter.convertQuestionToString(0, question));
    }
}