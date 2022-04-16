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

    @ShellMethod(value = "Get Book by Id (long id)", key = {"get-book", "gb"})
    public String getBookById(long id) {
        return bookCommands.getBookById(id);
    }

    @ShellMethod(value = "Add Book (String title, long idAuthor, long idGenre)", key = {"add-book", "ab"})
    public String insertBook(String title, long idAuthor, long idGenre) {
        return bookCommands.insertBook(title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Delete Book by Id (long id)", key = {"del-book", "db"})
    public String deleteBookById(long id) {
        return bookCommands.deleteBook(id);
    }

    @ShellMethod(value = "Update Book (long idBook, String title, long idAuthor, long idGenre)", key = {"upd-book", "ub"})
    public String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        return bookCommands.updateBook(idBook, title, idAuthor, idGenre);
    }

    @ShellMethod(value = "Get Book Comments (long idBook)", key = {"get-comments", "c"})
    public String getBookComments(long idBook) {
        return commentCommands.getComments(idBook);
    }

    @ShellMethod(value = "Add Comment (long idBook, String text)", key = {"add-comment", "ac"})
    public String addComment(long idBook, String text) {
        return commentCommands.addComment(idBook, text);
    }

    @ShellMethod(value = "Get Comment (long idComment)", key = {"get-comment", "gc"})
    public String getComment(long idComment) {
        return commentCommands.getComment(idComment);
    }

    @ShellMethod(value = "Update Comment (long idComment, String text)", key = {"upd-comment", "uc"})
    public String updateComment(long idComment, String text) {
        return commentCommands.updateComment(idComment, text);
    }

    @ShellMethod(value = "Delete Comment (long idComment)", key = {"del-comment", "dc"})
    public String deleteComment(long idComment) {
        return commentCommands.deleteComment(idComment);
    }

}
