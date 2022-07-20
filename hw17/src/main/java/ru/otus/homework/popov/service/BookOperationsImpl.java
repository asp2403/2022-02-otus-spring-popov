package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.exception.BadRequestException;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookOperationsImpl implements BookOperations {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public BookOperationsImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        var oldBook = bookRepository.findById(book.getId()).orElseThrow(NotFoundException::new);
        oldBook.setTitle(book.getTitle());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setGenre(book.getGenre());
        bookRepository.save(oldBook);
    }

    @Override
    public Book createBook(Book book) {
        if (book.getId() != null) {
            throw new BadRequestException();
        }
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(String id) {
        bookRepository.deleteById(id);
        commentRepository.deleteByBookId(id);
    }
}
