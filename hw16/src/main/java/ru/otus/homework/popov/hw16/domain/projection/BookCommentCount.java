package ru.otus.homework.popov.hw16.domain.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ru.otus.homework.popov.hw16.domain.Author;
import ru.otus.homework.popov.hw16.domain.Book;
import ru.otus.homework.popov.hw16.domain.Comment;
import ru.otus.homework.popov.hw16.domain.Genre;

import java.util.List;

@Projection(name = "bookCommentCount", types = {Book.class})

public interface BookCommentCount {
    String getTitle();

    Author getAuthor();

    Genre getGenre();

    @Value("#{target.getComments().size()}")
    int getCommentCount();
}
