package ru.otus.homework.popov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.popov.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
