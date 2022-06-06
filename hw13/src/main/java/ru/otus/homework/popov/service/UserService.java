package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<String> login(String username, String password);
    Optional<User> findByToken(String token);
    void logout(String username);

}
