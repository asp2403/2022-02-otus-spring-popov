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
import ru.otus.homework.popov.domain.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TestingServiceImplTest {

    @Mock
    private QuestionService questionService;

    @Mock
    private IOService ioService;

    @Mock
    private QuestionConverter questionConverter;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private TestingServiceImpl testingService;

    private List<Question> questions;

    @DisplayName("должен корректно тестировать пользователя")
    @Test
    void shouldCorrectTestUser() {
        var questions = Arrays.asList(
                new Question(0, "Question1", Arrays.asList(new Answer("Answer11", true), new Answer("Answer12", false))),
                new Question(1, "Question2", Arrays.asList(new Answer("Answer21", false), new Answer("Answer22", true))));
        given(questionService.loadQuestions()).willReturn(questions);
        given(questionConverter.convertQuestionToString(any())).willReturn("");
        given(ioService.readChar(any(), any())).willReturn('a');
        var user = new User("Vasya", "Pupkin");
        var testingResult = testingService.testUser(user);
        verify(ioService, times(2)).readChar(any(), any());
        assertAll(
                () -> assertEquals(1, testingResult.getScore()),
                () -> assertEquals(false, testingResult.isAborted())
        );

    }




}