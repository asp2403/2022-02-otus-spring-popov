package ru.otus.homework.popov.service.converter;

import ru.otus.homework.popov.domain.Comment;

public interface CommentConverter {
    String convertToString(Comment comment);
}