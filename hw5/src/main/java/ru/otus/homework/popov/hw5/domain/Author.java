package ru.otus.homework.popov.hw5.domain;

public class Author {
    private final long id;
    private final String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
