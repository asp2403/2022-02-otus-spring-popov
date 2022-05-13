package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Comment;

import java.util.List;

public interface CommentOperations {
    List<Comment> findByBookId(String bookId);
}
