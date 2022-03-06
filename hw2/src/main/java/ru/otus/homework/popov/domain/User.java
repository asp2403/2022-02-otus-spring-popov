package ru.otus.homework.popov.domain;

public class User {
    private final String name;
    private int score = 0;

    public User(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public int addScore() {
        score++;
        return score;
    }

    public int resetScore() {
        score = 0;
        return score;
    }
}
