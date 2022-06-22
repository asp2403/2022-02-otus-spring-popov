package ru.otus.homework.popov.hw14.domain.rdb;

import java.util.List;
import java.util.Objects;


public class RdbBook {

    private long id;

    private String title;

    private RdbAuthor author;

    private RdbGenre genre;

    private List<RdbComment> comments;

    public RdbBook() {}

    public RdbBook(long id, String title, RdbAuthor author, RdbGenre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public RdbAuthor getAuthor() {
        return author;
    }

    public RdbGenre getGenre() {
        return genre;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(RdbAuthor author) {
        this.author = author;
    }

    public void setGenre(RdbGenre genre) {
        this.genre = genre;
    }

    public List<RdbComment> getComments() {
        return comments;
    }

    public void setComments(List<RdbComment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdbBook book = (RdbBook) o;
        return id == book.id && title.equals(book.title) && author.equals(book.author) && genre.equals(book.genre) && Objects.equals(comments, book.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, comments);
    }
}
