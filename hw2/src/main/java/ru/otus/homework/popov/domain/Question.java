package ru.otus.homework.popov.domain;

import java.util.List;
import java.util.Objects;

public class Question {

    private final int index;
    private final String body;
    private final List<Answer> answers;

    public Question(int index, String body, List<Answer> answers) {
        this.index = index;
        this.body = body;
        this.answers = answers;
    }

    public String getBody() {
        return body;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getIndex() {
        return  index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return index == question.index && Objects.equals(body, question.body) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, body, answers);
    }

}
