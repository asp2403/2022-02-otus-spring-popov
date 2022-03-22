package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;

public interface QuestionConverter {
    String convertQuestionToString(Question question);
}
