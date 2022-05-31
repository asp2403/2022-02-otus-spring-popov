package ru.otus.homework.popov.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.service.AuthorOperations;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;
import ru.otus.homework.popov.service.GenreOperations;

import javax.validation.Valid;

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
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("mode", "add");
        return "book";
    }

    @GetMapping("/edit-book")
    public String editBook(@RequestParam("id") String id, Model model) {
        var book = bookOperations.findById(id).orElseThrow(NotFoundException::new);
        var bookDto = BookDto.fromDomainObject(book);
        var authors = authorOperations.findAll();
        var genres = genreOperations.findAll();
        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("mode", "edit");
        return "book";
    }

    @GetMapping("/book-details")
    public String showBookDetails(@RequestParam("id") String id, Model model) {
        var book = bookOperations.findById(id).orElseThrow(NotFoundException::new);
        var comments = commentOperations.findByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "book-details";
    }

    @Validated
    @PostMapping("/save-book")
    public String saveBook(@RequestParam("mode") String mode, @Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var authors = authorOperations.findAll();
            var genres = genreOperations.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            model.addAttribute("mode", mode);
            return "book";

        }
        bookOperations.save(bookDto);
        return "redirect:/";
    }

    @GetMapping("/del-book")
    public String delBookConfirm(@RequestParam("id") String id, Model model) {
        var bookTitle = bookOperations.findTitleById(id);
        model.addAttribute("bookTitle", bookTitle);
        model.addAttribute("id", id);
        return "del-book-confirm";
    }


    @PostMapping("/del-book")
    public String delBook(@RequestParam("id") String id) {
        bookOperations.delete(id);
        return "redirect:/";
    }
}
