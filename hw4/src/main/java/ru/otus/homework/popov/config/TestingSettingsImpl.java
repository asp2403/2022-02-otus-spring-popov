package ru.otus.homework.popov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.testing")
@Component
public class TestingSettingsImpl implements TestingSettings {

    private int scoreToPass;

    @Override
    public int getScoreToPass() {
        return scoreToPass;
    }

    public void setScoreToPass(int scoreToPass) {
        this.scoreToPass = scoreToPass;
    }
}
