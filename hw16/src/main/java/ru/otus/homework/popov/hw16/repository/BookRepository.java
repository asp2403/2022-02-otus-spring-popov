package ru.otus.homework.popov.hw16.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.homework.popov.hw16.domain.Book;
import ru.otus.homework.popov.hw16.domain.Comment;


import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>{

}
