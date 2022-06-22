package ru.otus.homework.popov.hw14.domain.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "books")
public class MongoBook {
    @Id
    private String id;
    private String title;
    private MongoAuthor author;
    private MongoGenre genre;
    private int commentCount;

    public MongoBook(String id, String title, MongoAuthor author, MongoGenre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public MongoBook(String title, MongoAuthor author, MongoGenre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public MongoBook(){}

    public void addComment(MongoComment comment) {
        comment.setBook(this);
        commentCount++;
    }

    public void delComment(MongoComment comment) {
        comment.setBook(null);
        commentCount--;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MongoAuthor getAuthor() {
        return author;
    }

    public void setAuthor(MongoAuthor author) {
        this.author = author;
    }

    public MongoGenre getGenre() {
        return genre;
    }

    public void setGenre(MongoGenre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoBook book = (MongoBook) o;
        return commentCount == book.commentCount && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre, commentCount);
    }
}
