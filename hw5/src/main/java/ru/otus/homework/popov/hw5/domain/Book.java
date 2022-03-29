package ru.otus.homework.popov.hw5.domain;

import java.util.Objects;

public class Book {
    private final long id;
    private final String title;
    private final long idAuthor;
    private final long idGenre;

    public Book(long id, String title, long idAuthor, long idGenre) {
        this.id = id;
        this.title = title;
        this.idAuthor = idAuthor;
        this.idGenre = idGenre;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getIdAuthor() {
        return idAuthor;
    }

    public long getIdGenre() {
        return idGenre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && idAuthor == book.idAuthor && idGenre == book.idGenre && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, idAuthor, idGenre);
    }
}
