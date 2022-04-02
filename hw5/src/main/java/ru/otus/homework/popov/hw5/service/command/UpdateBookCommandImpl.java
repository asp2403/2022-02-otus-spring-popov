package ru.otus.homework.popov.hw5.service.command;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw5.dao.AuthorDao;
import ru.otus.homework.popov.hw5.dao.BookDao;
import ru.otus.homework.popov.hw5.dao.GenreDao;
import ru.otus.homework.popov.hw5.domain.Book;
import ru.otus.homework.popov.hw5.service.localization.MessageService;

@Service
public class UpdateBookCommandImpl implements UpdateBookCommand {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;
    private final MessageService messageService;

    public UpdateBookCommandImpl(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao, MessageService messageService) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
        this.messageService = messageService;
    }

    @Override
    public String execute(long idBook, String title, long idAuthor, long idGenre) {
        try {
            var author = authorDao.getById(idAuthor);
            var genre = genreDao.getById(idGenre);
            var book = new Book(idBook, title, author, genre);
            bookDao.update(book);
            return messageService.getMessage("CMD_COMPLETE");
        } catch (EmptyResultDataAccessException e) {
            return messageService.getMessage("ERR_INTEGRITY");
        }
    }
}
