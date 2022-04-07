package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.dao.AuthorDao;
import ru.otus.homework.popov.dao.BookDao;
import ru.otus.homework.popov.dao.GenreDao;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.service.converter.BookConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class BookCommandsImpl implements BookCommands {
    private final BookDao bookDao;
    private final BookConverter bookConverter;
    private final MessageService messageService;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookCommandsImpl(BookDao bookDao, BookConverter bookConverter, MessageService messageService, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.bookConverter = bookConverter;
        this.messageService = messageService;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("BOOKS_LIST")).append(System.lineSeparator());
        var books = bookDao.getAll();
        books.forEach(book -> sb.append(bookConverter.convertToString(book)).append(System.lineSeparator()));
        return sb.toString();
    }

    @Override
    @Transactional
    public String insertBook(String title, long idAuthor, long idGenre) {
        var author = authorDao.getById(idAuthor);
        var genre = genreDao.getById(idGenre);
        if (author != null && genre != null) {
            var book = new Book(0, title, author, genre);
            bookDao.save(book);
            return messageService.getMessage("CMD_COMPLETE");
        } else {
            return messageService.getMessage("ERR_INTEGRITY");
        }
    }

    @Override
    @Transactional
    public String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        var book = bookDao.getById(idBook);
        if (book == null) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
        var author = authorDao.getById(idAuthor);
        var genre = genreDao.getById(idGenre);
        if (author != null && genre != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            bookDao.save(book);
            return messageService.getMessage("CMD_COMPLETE");
        } else {
            return messageService.getMessage("ERR_INTEGRITY");
        }
    }

    @Override
    @Transactional
    public String deleteBook(long idBook) {
        bookDao.deleteById(idBook);
        return messageService.getMessage("CMD_COMPLETE");
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookById(long idBook) {
        var book = bookDao.getById(idBook);
        if (book != null) {
            return bookConverter.convertToString(book);
        } else {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }
}
