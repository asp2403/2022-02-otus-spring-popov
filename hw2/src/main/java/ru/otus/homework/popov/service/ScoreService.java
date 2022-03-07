package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.User;

public interface ScoreService {

    int getScore();

    int addScore();

    void resetScore();
}
