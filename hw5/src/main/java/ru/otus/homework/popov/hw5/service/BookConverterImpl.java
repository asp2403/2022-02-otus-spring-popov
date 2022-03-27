package ru.otus.homework.popov.hw5.service;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.hw5.dao.AuthorDao;
import ru.otus.homework.popov.hw5.dao.GenreDao;
import ru.otus.homework.popov.hw5.domain.Book;

@Component
public class BookConverterImpl implements BookConverter {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookConverterImpl(AuthorDao authorDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public String convertToString(Book book) {
        var author = authorDao.getById(book.getIdAuthor());
        var genre = genreDao.getById(book.getIdGenre());
        return book.getId() + ": " + author.getName() + " \"" + book.getTitle() + "\" (" + genre.getName() + ")";
    }
}
