package ru.otus.homework.popov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.popov.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
