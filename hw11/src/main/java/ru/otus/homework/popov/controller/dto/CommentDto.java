package ru.otus.homework.popov.controller.dto;

import ru.otus.homework.popov.domain.Comment;

public class CommentDto {
    private String id;
    private String text;

    public CommentDto(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public static CommentDto fromDomainObject(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
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
