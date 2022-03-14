package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ConfigurationProperties(prefix = "application")
@Component
public class AppConfigImpl implements AppConfig {
    private int scoreToPass;
    private String resourceName;


    @Override
    public int getScoreToPass() {
        return scoreToPass;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    @Override
    public void setScoreToPass(int scoreToPass) {
        this.scoreToPass = scoreToPass;
    }

    @Override
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
