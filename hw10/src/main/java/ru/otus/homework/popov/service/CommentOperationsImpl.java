package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.repository.CommentRepository;

import java.util.List;

@Service
public class CommentOperationsImpl implements CommentOperations {
    private final CommentRepository commentRepository;

    public CommentOperationsImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookId(String bookId) {
        var comments = commentRepository.findByBookId(bookId);
        return comments;
    }
}
