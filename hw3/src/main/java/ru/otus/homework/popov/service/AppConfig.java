package ru.otus.homework.popov.service;

public interface AppConfig {
    int getScoreToPass();
    void setScoreToPass(int scoreToPass);
    String getResourceName();
    void setResourceName(String resourceName);
}
