package ru.otus.homework.popov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.popov.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
