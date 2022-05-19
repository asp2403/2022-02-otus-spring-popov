package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.popov.domain.Comment;

import java.util.List;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    @Query(fields = "{ 'text': 1 }")
    Flux<Comment> findByBookId(String bookId);

    Mono<Void> deleteByBookId(String bookId);
}
