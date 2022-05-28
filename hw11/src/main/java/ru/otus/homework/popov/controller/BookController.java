package ru.otus.homework.popov.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookController(BookRepository bookRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/api/books")
    public Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/api/books/{id}")
    public Mono<ResponseEntity<Book>> getBook(@PathVariable String id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.<Book>notFound().build()));

    }

    @GetMapping("/api/books/{id}/comments")
    public Flux<CommentDto> getComments(@PathVariable("id") String bookId) {
        return commentRepository.findByBookId(bookId)
                .map(CommentDto::fromDomainObject);

    }

    @PutMapping("/api/books")
    public Mono<ResponseEntity<Book>> updateBook(@RequestBody Book book) {
        return bookRepository.findById(book.getId())
                .map(b -> {
                    b.setTitle(book.getTitle());
                    b.setAuthor(book.getAuthor());
                    b.setGenre(book.getGenre());
                    bookRepository.save(b).subscribe();
                    return b;
                })
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.<Book>notFound().build()));
    }

    @PostMapping("/api/books")
    public Mono<ResponseEntity<Book>> createBook(@RequestBody Book book) {
        if (book.getId() != null) {
            return Mono.just(ResponseEntity.<Book>badRequest().build());
        }
        return bookRepository.save(book)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBook(@PathVariable String id) {
        return commentRepository.deleteByBookId(id).and(bookRepository.deleteById(id));

    }

}
