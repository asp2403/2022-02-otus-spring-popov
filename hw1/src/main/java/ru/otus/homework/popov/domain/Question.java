package ru.otus.homework.popov.domain;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(body, question.body) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, answers);
    }
}
