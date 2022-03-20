package ru.otus.homework.popov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.dao.QuestionDaoImpl;
import ru.otus.homework.popov.domain.Answer;
import ru.otus.homework.popov.domain.Question;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private QuestionServiceImpl questionService;


    @DisplayName("должен корректно загружать вопросы")
    @Test
    void shouldCorrectLoadQuestions() {
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
        given(questionDao.loadQuestions()).willReturn(questions);
        assertEquals(questions, questionService.loadQuestions());
    }
}