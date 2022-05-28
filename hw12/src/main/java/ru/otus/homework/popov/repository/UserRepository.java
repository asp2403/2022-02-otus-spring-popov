package ru.otus.homework.popov.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.popov.security.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
