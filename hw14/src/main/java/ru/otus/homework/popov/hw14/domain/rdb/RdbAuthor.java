package ru.otus.homework.popov.hw14.domain.rdb;

import ru.otus.homework.popov.hw14.domain.mongo.MongoAuthor;

import java.util.Objects;

public class RdbAuthor {

    private long id;

    private String name;

    public RdbAuthor(){}

    public RdbAuthor(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MongoAuthor toMongo(RdbAuthor rdbAuthor) {
        return new MongoAuthor(String.valueOf(rdbAuthor.getId()), rdbAuthor.getName());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdbAuthor author = (RdbAuthor) o;
        return id == author.id && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
