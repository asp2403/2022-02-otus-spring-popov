package ru.otus.homework.popov.service;

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

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @DisplayName("возвращает корректный набор данных")
    @Test
    void shouldCorrectGetQuestions() {
        var questions = Arrays.asList(
                new Question("Question1", Arrays.asList(new Answer("Answer11", true), new Answer("Answer12", false))),
                new Question("Question2", Arrays.asList(new Answer("Answer21", false), new Answer("Answer22", true))));
        given(questionDao.getQuestions()).willReturn(questions);
        assertEquals(questionService.getQuestions(), questions);
    }
}