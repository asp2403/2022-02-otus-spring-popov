package ru.otus.homework.popov.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.homework.popov.service.command.AuthorCommands;
import ru.otus.homework.popov.service.command.BookCommands;
import ru.otus.homework.popov.service.command.CommentCommands;
import ru.otus.homework.popov.service.command.GenreCommands;

@ShellComponent
public class AppCommands {
    private final AuthorCommands authorCommands;
    private final GenreCommands genreCommands;
    private final BookCommands bookCommands;
    private final CommentCommands commentCommands;

    public AppCommands(AuthorCommands authorCommands, GenreCommands genreCommands, BookCommands bookCommands, CommentCommands commentCommands) {
        this.authorCommands = authorCommands;
        this.genreCommands = genreCommands;
        this.bookCommands = bookCommands;
        this.commentCommands = commentCommands;
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

    @ShellMethod(value = "Add Book (String title, String idAuthor, String idGenre)", key = {"add-book", "ab"})
    public String insertBook(String title, String idAuthor, String idGenre) {
        return bookCommands.insertBook(title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Update Book (String idBook, String title, String idAuthor, String idGenre)", key = {"upd-book", "ub"})
    public String updateBook(String idBook, String title, String idAuthor, String idGenre) {
        return bookCommands.updateBook(idBook, title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Delete Book by Id (String id)", key = {"del-book", "db"})
    public String deleteBookById(String id) {
        return bookCommands.deleteBook(id);
    }

    @ShellMethod(value = "Get Book by Id (String id)", key = {"get-book", "gb"})
    public String getBookById(String id) {
        return bookCommands.getBookById(id);
    }

    @ShellMethod(value = "Get Book Comments (String idBook)", key = {"get-comments", "c"})
    public String getBookComments(String idBook) {
        return commentCommands.getComments(idBook);
    }

    @ShellMethod(value = "Add Comment (String idBook, String text)", key = {"add-comment", "ac"})
    public String addComment(String idBook, String text) {
        return commentCommands.addComment(idBook, text);
    }

    @ShellMethod(value = "Update Comment (String idComment, String text)", key = {"upd-comment", "uc"})
    public String updateComment(String idComment, String text) {
        return commentCommands.updateComment(idComment, text);
    }

    @ShellMethod(value = "Delete Comment (String idComment)", key = {"del-comment", "dc"})
    public String deleteComment(String idComment) {
        return commentCommands.deleteComment(idComment);
    }

    @ShellMethod(value = "Get Comment (String idComment)", key = {"get-comment", "gc"})
    public String getComment(String idComment) {
        return commentCommands.getComment(idComment);
    }
}
