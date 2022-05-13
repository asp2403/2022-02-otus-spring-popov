package ru.otus.homework.popov.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.exception.BadRequestException;
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
        Book book;
        try {
            book = bookOperations.findById(id).orElseThrow(NotFoundException::new);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return book;
    }

    @GetMapping("/books/{id}/comments")
    public List<CommentDto> getComments(@PathVariable("id") String bookId) {
        var comments = commentOperations.findByBookId(bookId);
        var commentDtos = comments.stream().map(CommentDto::fromDomainObject).collect(Collectors.toList());
        return commentDtos;
    }

    @PutMapping("/books")
    public void updateBook(@RequestBody Book book) {
        try {
            bookOperations.updateBook(book);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        try {
            return bookOperations.createBook(book);
        } catch (BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable String id) {
        bookOperations.delete(id);
    }
}
