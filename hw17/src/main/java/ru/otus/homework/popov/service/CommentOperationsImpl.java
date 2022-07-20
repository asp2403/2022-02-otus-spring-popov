package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentOperationsImpl implements CommentOperations {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentOperationsImpl(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookId(String bookId) {
        var comments = commentRepository.findByBookId(bookId);
        return comments;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment addComment(Book book, Comment comment) {
        book.addComment(comment);
        bookRepository.save(book);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delComment(Comment comment) {
        var book = comment.getBook();
        book.delComment(comment);
        bookRepository.save(book);
        commentRepository.delete(comment);
    }
}
