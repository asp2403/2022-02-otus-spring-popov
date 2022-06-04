package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.popov.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
    void deleteByBookId(String bookId);
}
