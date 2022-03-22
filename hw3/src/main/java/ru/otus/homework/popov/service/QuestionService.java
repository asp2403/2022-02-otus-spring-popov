package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> loadQuestions();

}
