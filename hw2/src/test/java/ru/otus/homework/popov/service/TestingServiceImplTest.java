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
    private QuestionService questionService;

    private ScoreService scoreService;

    private TestingServiceImpl testingService;

    private List<Question> questions;


    @BeforeEach
    private void setUp() {
        questions = Arrays.asList(
                new Question(0, "Question1", Arrays.asList(new Answer("Answer11", true), new Answer("Answer12", false))),
                new Question(1, "Question2", Arrays.asList(new Answer("Answer21", false), new Answer("Answer22", true))));
        given(questionService.loadQuestions()).willReturn(questions);
        scoreService = new ScoreServiceImpl();
        testingService = new TestingServiceImpl(questionService, scoreService);
        testingService.startTest();
    }

    @DisplayName("должен корректно учитывать ответ на вопрос")
    @Test
    void shouldCorrectAnswerQuestion() {
        var question = testingService.getNextQuestion();
        while (question != null) {
            testingService.answerQuestion(0);
            question = testingService.getNextQuestion();
        }
        assertEquals(1, testingService.getScore());
    }

    @DisplayName("должен корректно обрабатывать повторную попытку")
    @Test
    void shouldCorrectTryAgain() {
        var question = testingService.getNextQuestion();
        while (question != null) {
            testingService.answerQuestion(0);
            question = testingService.getNextQuestion();
        }
        testingService.startTest();
        assertEquals(0, testingService.getScore());
    }

}