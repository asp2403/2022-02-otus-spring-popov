package ru.otus.homework.popov.controller.dto;

import ru.otus.homework.popov.domain.Comment;

public class CommentDto {
    private String id;
    private String text;
    private String author;
    private String bookId;

    public CommentDto(String id, String text, String author, String bookId) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.bookId = bookId;
    }

    public static CommentDto fromDomainObject(Comment comment) {
        var bookId = comment.getBook() != null ? comment.getBook().getId() : null;
        return new CommentDto(comment.getId(), comment.getText(), comment.getAuthor(), bookId);
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
