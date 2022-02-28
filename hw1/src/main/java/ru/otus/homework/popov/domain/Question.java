package ru.otus.homework.popov.domain;

import java.util.List;

public class Question {
    private final String body;
    private final List<String> answers;


    public Question(String body, List<String> answers) {
        this.body = body;
        this.answers = answers;
    }

    public String getBody() {
        return body;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
