package ru.otus.homework.popov.domain;

public class TestingResult {
    private final User user;
    private int score;
    private boolean aborted;

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

    public boolean isAborted() {
        return aborted;
    }

    public void setAborted(boolean aborted) {
        this.aborted = aborted;
    }
}
