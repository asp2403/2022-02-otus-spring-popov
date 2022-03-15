package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ConfigurationProperties(prefix = "application")
@Component
public class AppConfigImpl implements AppConfig {
    private int scoreToPass;
    private String questionsBaseName = "";
    private String locale = "";

    @Override
    public int getScoreToPass() {
        return scoreToPass;
    }

    @Override
    public String getQuestionsBaseName() {
        return questionsBaseName;
    }

    public void setScoreToPass(int scoreToPass) {
        this.scoreToPass = scoreToPass;
    }

    public void setQuestionsBaseName(String questionsBaseName) {
        this.questionsBaseName = questionsBaseName;
    }

    @Override
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
