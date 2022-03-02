package ru.otus.homework.popov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDaoImplTest {

    @DisplayName("должен корректно читать файл ресурсов")
    @Test
    void shouldCorrectGetQuestions() {
        var questions = Arrays.asList(
                new Question("Question1", Arrays.asList(
                        new Answer("Answer11", true),
                        new Answer("Answer12", false)
                )),
                new Question("Question2", Arrays.asList(
                        new Answer("Answer21", false),
                        new Answer("Answer22", false),
                        new Answer("Answer23", true)
                )),
                new Question("Question3", Arrays.asList(
                        new Answer("Answer31", false),
                        new Answer("Answer32", true),
                        new Answer("Answer33", false),
                        new Answer("Answer34", false)
                ))
        );
        var dao = new QuestionDaoImpl("test.csv");
        assertEquals(questions, dao.getQuestions());
    }
}