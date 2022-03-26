package ru.otus.homework.popov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.ui")
@Component
public class UISettingsImpl implements UISettings {
    private char cmdQuit = 'q';
    private char cmdYes = 'y';
    private String prompt = ">";

    @Override
    public char getCmdQuit() {
        return cmdQuit;
    }

    @Override
    public char getCmdYes() {
        return cmdYes;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    public void setCmdQuit(char cmdQuit) {
        this.cmdQuit = cmdQuit;
    }

    public void setCmdYes(char cmdYes) {
        this.cmdYes = cmdYes;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
