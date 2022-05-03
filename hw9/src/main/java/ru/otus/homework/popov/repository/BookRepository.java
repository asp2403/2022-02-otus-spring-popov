package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.popov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>{
    List<Book> findByTitle(String Title);

    @Query(value = "{ 'id' : :#{#id} }", fields = "{ 'title' : 1, 'id' : 0 }")
    Optional<Book> findTitle(@Param("id") String id);
}
