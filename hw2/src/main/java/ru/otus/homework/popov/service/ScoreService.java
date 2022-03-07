package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.User;

public interface ScoreService {

    void registerUser(String userName);

    User getUser();

    int getScore();

    int addScore();

    void resetScore();

}
