package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookOperationsImpl implements BookOperations {
    private final BookRepository bookRepository;

    public BookOperationsImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}
