package ru.otus.homework.popov.hw14.domain.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "comments")
public class MongoComment {
    @Id
    private String id;
    private String text;
    @DBRef
    private MongoBook book;

    public MongoComment(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public MongoComment() {}

    public MongoBook getBook() {
        return book;
    }

    public void setBook(MongoBook book) {
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoComment comment = (MongoComment) o;
        return Objects.equals(id, comment.id) && Objects.equals(text, comment.text) && Objects.equals(book, comment.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, book);
    }
}
