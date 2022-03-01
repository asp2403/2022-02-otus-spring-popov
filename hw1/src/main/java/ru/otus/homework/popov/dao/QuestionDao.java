package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestions();
}
