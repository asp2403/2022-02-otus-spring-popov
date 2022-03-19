package ru.otus.homework.popov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.localization")
@Component
public class LocalizationSettingsImpl implements LocalizationSettings {
    private String questionsBaseName = "";
    private String locale = "";

    @Override
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String getQuestionsBaseName() {
        return questionsBaseName;
    }

    public void setQuestionsBaseName(String questionsBaseName) {
        this.questionsBaseName = questionsBaseName;
    }
}
