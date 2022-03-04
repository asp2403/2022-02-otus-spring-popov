package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private TestingServiceImpl testingService;

    private String userName = "Vasya";

    private List<Question> questions;


    @BeforeEach
    private void setUp() {
        questions = Arrays.asList(
                new Question("Question1", Arrays.asList(new Answer("Answer11", true), new Answer("Answer12", false))),
                new Question("Question2", Arrays.asList(new Answer("Answer21", false), new Answer("Answer22", true))));
        given(questionDao.loadQuestions()).willReturn(questions);
        testingService.registerUser(userName);
    }

    @DisplayName("должен корректно регистрировать пользователя")
    @Test
    void shouldCorrectRegisterUser() {
        assertAll(
                () -> assertEquals(userName, testingService.getUser().getName()),
                () -> assertEquals(0, testingService.getUser().getScore())
        );
    }

    @DisplayName("должен корректно учитывать ответ на вопрос")
    @Test
    void shouldCorrectAnswerQuestion() {
        testingService.answerQuestion(0, 0);
        testingService.answerQuestion(1, 0);
        assertEquals(1, testingService.getUser().getScore());
    }

    @DisplayName("должен корректно обрабатывать повторную попытку")
    @Test
    void shouldCorrectTryAgain() {
        testingService.answerQuestion(0, 0);
        testingService.answerQuestion(1, 0);
        testingService.tryAgain();
        assertEquals(0, testingService.getUser().getScore());
    }

    @DisplayName("должен возвращать правильный вопрос")
    @Test
    void shouldCorrectGetQuestion() {
        var question = questions.get(1);
        assertEquals(question, testingService.getQuestion(1));
    }

    @DisplayName("должен корректно возвращать количество вопросов")
    @Test
    void shouldCorrectGetQuestionCount() {
        assertEquals(questions.size(), testingService.getQuestionCount());
    }
}