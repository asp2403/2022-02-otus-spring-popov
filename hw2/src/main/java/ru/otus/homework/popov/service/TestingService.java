package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

public interface TestingService {
    void startTest(String userName);

    void answerQuestion(int answerIndex);

    Question getNextQuestion();

    int getScore();

    void resetTest();
}
