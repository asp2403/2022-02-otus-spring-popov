package ru.otus.homework.popov.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework.popov.domain.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByTitle(String Title);
}
