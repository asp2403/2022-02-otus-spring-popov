package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.User;

@Service
public class ScoreServiceImpl implements ScoreService {

    private int score;

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public int addScore() {
        score++;
        return score;
    }

    @Override
    public void resetScore() {
        score = 0;
    }
}
