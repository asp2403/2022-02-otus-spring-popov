package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppConfigImpl implements AppConfig {
    private final int scoreToPass;

    public AppConfigImpl(@Value("${scoreToPass}") int scoreToPass) {
        this.scoreToPass = scoreToPass;
    }

    @Override
    public int getScoreToPass() {
        return scoreToPass;
    }
}
