package ru.otus.homework.popov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.popov.dao.QuestionDao;
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
    void getQuestions() {
        given(questionDao.getQuestions()).willReturn(Arrays.asList(
                new Question("Question1", Arrays.asList("Answer11", "Answer12")),
                new Question("Question2", Arrays.asList("Answer21", "Answer22"))));
        assertEquals(questionService.getQuestions(), questionDao.getQuestions());
    }
}