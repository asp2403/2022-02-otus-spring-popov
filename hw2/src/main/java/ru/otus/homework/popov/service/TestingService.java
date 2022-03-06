package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

public interface TestingService {

    void registerUser(String userName);

    void answerQuestion(int questionIndex, int answerIndex);

    void tryAgain();

    User getUser();

    Question getQuestion(int index);

    int getQuestionCount();

}
