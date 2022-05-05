package ru.otus.homework.popov.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookOperations bookOperations;
    private final CommentOperations commentOperations;

    public BookController(BookOperations bookOperations, CommentOperations commentOperations) {
        this.bookOperations = bookOperations;
        this.commentOperations = commentOperations;
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookOperations.findAll();
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable String id) {
        var book = bookOperations.findById(id).orElseThrow(NotFoundException::new);
        return book;
    }

    @GetMapping("/books/{id}/comments")
    public List<CommentDto> getComments(@PathVariable("id") String bookId) {
        var comments = commentOperations.findByBookId(bookId);
        var commentDtos = comments.stream().map(CommentDto::fromDomainObject).collect(Collectors.toList());
        return commentDtos;
    }
}
