package ru.otus.homework.popov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.service.AppConfig;
import ru.otus.homework.popov.service.LocaleProvider;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionDaoImplTest {
    @Autowired
    private AppConfig appConfig;

    @Autowired
    private LocaleProvider localeProvider;

    @DisplayName("должен корректно читать файл ресурсов")
    @Test
    void shouldCorrectGetQuestions() {
        var questions = Arrays.asList(
                new Question(0, "Question1", Arrays.asList(
                        new Answer("Answer11", true),
                        new Answer("Answer12", false)
                )),
                new Question(1, "Question2", Arrays.asList(
                        new Answer("Answer21", false),
                        new Answer("Answer22", false),
                        new Answer("Answer23", true)
                )),
                new Question(2, "Question3", Arrays.asList(
                        new Answer("Answer31", false),
                        new Answer("Answer32", true),
                        new Answer("Answer33", false),
                        new Answer("Answer34", false)
                ))
        );
        var dao = new QuestionDaoImpl(appConfig, localeProvider);
        assertEquals(questions, dao.loadQuestions());
    }
}