package ru.otus.homework.popov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.service.AuthorOperations;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.GenreOperations;

@Controller
public class BookController {

    private final BookOperations bookOperations;
    private final AuthorOperations authorOperations;
    private final GenreOperations genreOperations;


    public BookController(BookOperations bookOperations, AuthorOperations authorOperations, GenreOperations genreOperations) {
        this.bookOperations = bookOperations;
        this.authorOperations = authorOperations;
        this.genreOperations = genreOperations;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        var books = bookOperations.findAll();
        model.addAttribute("title", "Список книг");
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/edit-book")
    public String bookPage(@RequestParam("id") String id, Model model) {
        var book = bookOperations.findById(id).orElseThrow(NotFoundException::new);
        var authors = authorOperations.findAll();
        var genres = genreOperations.findAll();
        model.addAttribute("title", "Редактировать книгу");
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book";
    }
}
