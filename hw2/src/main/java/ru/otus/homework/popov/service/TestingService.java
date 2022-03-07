package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

public interface TestingService {
    void startTest();

    void answerQuestion(int answerIndex);

    Question getNextQuestion();

    int getScore();

}
