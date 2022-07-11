package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.homework.popov.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(fields = "{ 'text': 1, 'author': 1 }")
    List<Comment> findByBookId(String bookId);

    void deleteByBookId(String bookId);
}
