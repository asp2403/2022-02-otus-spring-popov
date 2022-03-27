package ru.otus.homework.popov.hw5.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.hw5.dao.AuthorDao;
import ru.otus.homework.popov.hw5.dao.BookDao;
import ru.otus.homework.popov.hw5.dao.GenreDao;
import ru.otus.homework.popov.hw5.domain.Book;
import ru.otus.homework.popov.hw5.service.AuthorConverter;
import ru.otus.homework.popov.hw5.service.BookConverter;
import ru.otus.homework.popov.hw5.service.GenreConverter;

@ShellComponent
public class AppCommands {

    private final AuthorConverter authorConverter;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final GenreConverter genreConverter;
    private final BookDao bookDao;
    private final BookConverter bookConverter;

    public AppCommands(AuthorDao authorDao, AuthorConverter authorConverter, GenreDao genreDao, GenreConverter genreConverter, BookDao bookDao, BookConverter bookConverter) {
        this.authorConverter = authorConverter;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.genreConverter = genreConverter;
        this.bookDao = bookDao;
        this.bookConverter = bookConverter;
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

    @ShellMethod(value = "Get Book by Id", key = {"get-book", "gb"})
    public String commandGetBookById(long id) {
        return getBook(id);
    }

    @ShellMethod(value = "Insert Book", key = {"ins-book", "ib"})
    public void commandInsertBook(String title, long idAuthor, long idGenre) {
        insertBook(title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Update Book", key = {"upd-book", "ub"})
    public void commandUpdateBook(long idBook, String title, long idAuthor, long idGenre) {
        updateBook(idBook, title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Delete Book by Id", key = {"del-book", "db"})
    public void commandDeleteBookById(long id) {
        deleteBook(id);
    }

    private String getAllAuthors() {
        var sb = new StringBuilder("Список авторов: \n");
        var authors = authorDao.getAll();
        authors.forEach(author -> sb.append(authorConverter.convertToString(author)).append("\n"));
        return sb.toString();
    }

    private String getAllGenres() {
        var sb = new StringBuilder("Список жанров: \n");
        var genres = genreDao.getAll();
        genres.forEach(genre -> sb.append(genreConverter.convertToString(genre)).append("\n"));
        return sb.toString();
    }

    private String getAllBooks() {
        var sb = new StringBuilder("Список книг: \n");
        var books = bookDao.getAll();
        books.forEach(genre -> sb.append(bookConverter.convertToString(genre)).append("\n"));
        return sb.toString();
    }

    private String getBook(long id) {
        var book = bookDao.getById(id);
        return bookConverter.convertToString(book);
    }

    private void insertBook(String title, long idAuthor, long idGenre) {
        var book = new Book(0, title, idAuthor, idGenre);
        bookDao.insert(book);
    }

    private void updateBook(long idBook, String title, long idAuthor, long idGenre) {
        var book = new Book(idBook, title, idAuthor, idGenre);
        bookDao.update(book);
    }

    private void deleteBook(long id) {
        bookDao.deleteById(id);
    }
}
