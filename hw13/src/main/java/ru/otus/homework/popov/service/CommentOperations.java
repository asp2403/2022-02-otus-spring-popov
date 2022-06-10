package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentOperations {
    List<Comment> findByBookId(String bookId);
    Optional<Comment> findById(String id);
    Comment addComment(Book book, Comment comment);
    void delComment(Comment comment);
}
