package ru.otus.homework.popov.hw5.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.hw5.service.command.*;

@ShellComponent
public class AppCommands {

    private final GetAllAuthorsCommand getAllAuthorsCommand;
    private final GetAllGenresCommand getAllGenresCommand;
    private final GetAllBooksCommand getAllBooksCommand;
    private final GetBookByIdCommand getBookByIdCommand;
    private final InsertBookCommand insertBookCommand;
    private final UpdateBookCommand updateBookCommand;
    private final DeleteBookByIdCommand deleteBookByIdCommand;

    public AppCommands(GetAllAuthorsCommand getAllAuthorsCommand, GetAllGenresCommand getAllGenresCommand, GetAllBooksCommand getAllBooksCommand, GetBookByIdCommand getBookByIdCommand, InsertBookCommand insertBookCommand, UpdateBookCommand updateBookCommand, DeleteBookByIdCommand deleteBookByIdCommand) {
        this.getAllAuthorsCommand = getAllAuthorsCommand;
        this.getAllGenresCommand = getAllGenresCommand;
        this.getAllBooksCommand = getAllBooksCommand;
        this.getBookByIdCommand = getBookByIdCommand;
        this.insertBookCommand = insertBookCommand;
        this.updateBookCommand = updateBookCommand;
        this.deleteBookByIdCommand = deleteBookByIdCommand;
    }

    @ShellMethod(value = "Get All Authors", key = {"authors", "a"})
    public String getAllAuthors() {
        return getAllAuthorsCommand.execute();
    }

    @ShellMethod(value = "Get All Genres", key = {"genres", "g"})
    public String getAllGenres() {
        return getAllGenresCommand.execute();
    }

    @ShellMethod(value = "Get All Books", key = {"books", "b"})
    public String getAllBooks() {
        return getAllBooksCommand.execute();
    }

    @ShellMethod(value = "Get Book by Id (long id)", key = {"get-book", "gb"})
    public String getBookById(long id) {
        return getBookByIdCommand.execute(id);
    }

    @ShellMethod(value = "Insert Book (String title, long idAuthor, long idGenre)", key = {"ins-book", "ib"})
    public String insertBook(String title, long idAuthor, long idGenre) {
        return insertBookCommand.execute(title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Update Book (long idBook, String title, long idAuthor, long idGenre)", key = {"upd-book", "ub"})
    public String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        return updateBookCommand.execute(idBook, title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Delete Book by Id (long id)", key = {"del-book", "db"})
    public String deleteBookById(long id) {
        return deleteBookByIdCommand.execute(id);
    }
}
