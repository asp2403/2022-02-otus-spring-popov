package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.TestingResult;
import ru.otus.homework.popov.domain.User;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public interface TestingService {

    TestingResult testUser(User user);

}
