package ru.otus.homework.popov.domain;

public class TestingResult {
    private final User user;
    private int score;

    public TestingResult(User user) {
        this.user = user;
    }

    public void applyAnswer(boolean answerResult) {
        if (answerResult) {
            score++;
        }
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }
}
