package ru.otus.homework.popov.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.service.AuthorCommands;
import ru.otus.homework.popov.service.BookCommands;
import ru.otus.homework.popov.service.GenreCommands;

@ShellComponent
public class AppCommands {

    private final AuthorCommands authorCommands;
    private final GenreCommands genreCommands;
    private final BookCommands bookCommands;

    public AppCommands(AuthorCommands authorCommands, GenreCommands genreCommands, BookCommands bookCommands) {
        this.authorCommands = authorCommands;
        this.genreCommands = genreCommands;
        this.bookCommands = bookCommands;
    }

    @ShellMethod(value = "Get All Authors", key = {"authors", "a"})
    String getAllAuthors() {
        return authorCommands.getAll();
    }

    @ShellMethod(value = "Get All Genres", key = {"genres", "g"})
    String getAllGenres() {
        return genreCommands.getAll();
    }

    @ShellMethod(value = "Get All Books", key = {"books", "b"})
    public String getAllBooks() {
        return bookCommands.getAll();
    }

    @ShellMethod(value = "Insert Book (String title, long idAuthor, long idGenre)", key = {"ins-book", "ib"})
    public String insertBook(String title, long idAuthor, long idGenre) {
        return bookCommands.insertBook(title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Get Book by Id (long id)", key = {"get-book", "gb"})
    public String getBookById(long id) {
        return bookCommands.getBookById(id);
    }

    @ShellMethod(value = "Update Book (long idBook, String title, long idAuthor, long idGenre)", key = {"upd-book", "ub"})
    public String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        return bookCommands.updateBook(idBook, title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Delete Book by Id (long id)", key = {"del-book", "db"})
    public String deleteBookById(long id) {
        return bookCommands.deleteBook(id);
    }
}
