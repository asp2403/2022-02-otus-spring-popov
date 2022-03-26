package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> register();
}
