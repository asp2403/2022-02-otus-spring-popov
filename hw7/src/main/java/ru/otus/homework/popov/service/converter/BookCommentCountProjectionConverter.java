package ru.otus.homework.popov.service.converter;

import ru.otus.homework.popov.projection.BookCommentCountProjection;


public interface BookCommentCountProjectionConverter {
    String convertToString(BookCommentCountProjection bookCommentCountProjection);
}
