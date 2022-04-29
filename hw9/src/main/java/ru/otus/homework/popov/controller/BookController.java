package ru.otus.homework.popov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.service.AuthorOperations;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;
import ru.otus.homework.popov.service.GenreOperations;

@Controller
public class BookController {

    private final BookOperations bookOperations;
    private final AuthorOperations authorOperations;
    private final GenreOperations genreOperations;
    private final CommentOperations commentOperations;


    public BookController(BookOperations bookOperations, AuthorOperations authorOperations, GenreOperations genreOperations, CommentOperations commentOperations) {
        this.bookOperations = bookOperations;
        this.authorOperations = authorOperations;
        this.genreOperations = genreOperations;
        this.commentOperations = commentOperations;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        var books = bookOperations.findAll();
        model.addAttribute("title", "Список книг");
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/add-book")
    public String addBook(Model model) {
        var book = new Book();
        var authors = authorOperations.findAll();
        var genres = genreOperations.findAll();
        model.addAttribute("title", "Добавить книгу");
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book";
    }

    @GetMapping("/edit-book")
    public String editBook(@RequestParam("id") String id, Model model) {
        var book = bookOperations.findById(id).orElseThrow(NotFoundException::new);
        var authors = authorOperations.findAll();
        var genres = genreOperations.findAll();
        var comments = commentOperations.findByBookId(id);
        model.addAttribute("title", "Редактировать книгу");
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("comments", comments);
        return "book";
    }

    @PostMapping("/save-book")
    public String saveBook(BookDto bookDto) {
        bookOperations.save(bookDto);
        return "redirect:/";
    }

    @GetMapping("/del-book")
    public String delBook(@RequestParam("id") String id) {
        bookOperations.delete(id);
        return "redirect:/";
    }
}
