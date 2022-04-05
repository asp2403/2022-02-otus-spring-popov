package ru.otus.homework.popov.dao;

import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> getAll(Book book);
    Comment getById(long id);
    Comment save(Comment comment);
    void deleteById(long id);

}
