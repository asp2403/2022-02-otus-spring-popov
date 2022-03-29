package ru.otus.homework.popov.hw5.shell;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.hw5.dao.AuthorDao;
import ru.otus.homework.popov.hw5.dao.BookDao;
import ru.otus.homework.popov.hw5.dao.GenreDao;
import ru.otus.homework.popov.hw5.domain.Book;
import ru.otus.homework.popov.hw5.service.AuthorConverter;
import ru.otus.homework.popov.hw5.service.BookConverter;
import ru.otus.homework.popov.hw5.service.GenreConverter;
import ru.otus.homework.popov.hw5.service.MessageService;

@ShellComponent
public class AppCommands {

    private final AuthorConverter authorConverter;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final GenreConverter genreConverter;
    private final BookDao bookDao;
    private final BookConverter bookConverter;
    private final MessageService messageService;

    public AppCommands(AuthorDao authorDao, AuthorConverter authorConverter, GenreDao genreDao, GenreConverter genreConverter, BookDao bookDao, BookConverter bookConverter, MessageService messageService) {
        this.authorConverter = authorConverter;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.genreConverter = genreConverter;
        this.bookDao = bookDao;
        this.bookConverter = bookConverter;
        this.messageService = messageService;
    }

    @ShellMethod(value = "Get All Authors", key = {"authors", "a"})
    public String commandGetAllAuthors() {
        return getAllAuthors();
    }

    @ShellMethod(value = "Get All Genres", key = {"genres", "g"})
    public String commandGetAllGenres() {
        return getAllGenres();
    }

    @ShellMethod(value = "Get All Books", key = {"books", "b"})
    public String commandGetAllBooks() {
        return getAllBooks();
    }

    @ShellMethod(value = "Get Book by Id (long id)", key = {"get-book", "gb"})
    public String commandGetBookById(long id) {
        return getBook(id);
    }

    @ShellMethod(value = "Insert Book (String title, long idAuthor, long idGenre)", key = {"ins-book", "ib"})
    public String commandInsertBook(String title, long idAuthor, long idGenre) {
        return insertBook(title, idAuthor, idGenre);
    }
    @ShellMethod(value = "Update Book (long idBook, String title, long idAuthor, long idGenre)", key = {"upd-book", "ub"})
    public String commandUpdateBook(long idBook, String title, long idAuthor, long idGenre) {
        return updateBook(idBook, title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Delete Book by Id (long id)", key = {"del-book", "db"})
    public String commandDeleteBookById(long id) {
        return deleteBook(id);
    }

    private String getAllAuthors() {
        var sb = new StringBuilder(messageService.getMessage("AUTHORS_LIST")).append(System.lineSeparator());
        var authors = authorDao.getAll();
        authors.forEach(author -> sb.append(authorConverter.convertToString(author)).append(System.lineSeparator()));
        return sb.toString();
    }

    private String getAllGenres() {
        var sb = new StringBuilder(messageService.getMessage("GENRES_LIST")).append(System.lineSeparator());
        var genres = genreDao.getAll();
        genres.forEach(genre -> sb.append(genreConverter.convertToString(genre)).append(System.lineSeparator()));
        return sb.toString();
    }

    private String getAllBooks() {
        var sb = new StringBuilder(messageService.getMessage("BOOKS_LIST")).append(System.lineSeparator());
        var books = bookDao.getAll();
        books.forEach(genre -> sb.append(bookConverter.convertToString(genre)).append(System.lineSeparator()));
        return sb.toString();
    }

    private String getBook(long id) {
        try {
            var book = bookDao.getById(id);
            return bookConverter.convertToString(book);
        } catch (EmptyResultDataAccessException e) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }

    private String insertBook(String title, long idAuthor, long idGenre) {
        try {
            var book = new Book(0, title, idAuthor, idGenre);
            bookDao.insert(book);
            return messageService.getMessage("CMD_COMPLETE");
        } catch (DataIntegrityViolationException e) {
            return messageService.getMessage("ERR_INTEGRITY");
        }
    }

    private String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        try {
            var book = new Book(idBook, title, idAuthor, idGenre);
            bookDao.update(book);
            return messageService.getMessage("CMD_COMPLETE");
        } catch (DataIntegrityViolationException e) {
            return messageService.getMessage("ERR_INTEGRITY");
        }
    }

    private String deleteBook(long id) {
        bookDao.deleteById(id);
        return messageService.getMessage("CMD_COMPLETE");
    }
}
