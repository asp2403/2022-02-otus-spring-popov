package ru.otus.homework.popov.hw14.domain.rdb;

import java.util.Objects;

public class RdbComment {
    private long id;
    private String text;
    private RdbBook book;

    public RdbComment() {
    }

    public RdbComment(long id, String text, RdbBook book) {
        this.id = id;
        this.text = text;
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RdbBook getBook() {
        return book;
    }

    public void setBook(RdbBook book) {
        this.book = book;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdbComment comment = (RdbComment) o;
        return id == comment.id && Objects.equals(text, comment.text) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
