package ru.otus.homework.popov.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.service.command.AuthorCommands;
import ru.otus.homework.popov.service.command.BookCommands;
import ru.otus.homework.popov.service.command.GenreCommands;

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

    @ShellMethod(value = "Get Book by Id (long id)", key = {"get-book", "gb"})
    public String getBookById(long id) {
        return bookCommands.getBookById(id);
    }

    @ShellMethod(value = "Add Book (String title, long idAuthor, long idGenre)", key = {"add-book", "ab"})
    public String insertBook(String title, long idAuthor, long idGenre) {
        return bookCommands.insertBook(title, idAuthor, idGenre);
    }
}
