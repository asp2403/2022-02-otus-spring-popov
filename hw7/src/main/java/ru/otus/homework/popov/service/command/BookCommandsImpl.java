package ru.otus.homework.popov.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.GenreRepository;
import ru.otus.homework.popov.service.converter.BookConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class BookCommandsImpl implements BookCommands {
    private final BookConverter bookConverter;
    private final MessageService messageService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookCommandsImpl(BookConverter bookConverter, MessageService messageService, BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookConverter = bookConverter;
        this.messageService = messageService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("BOOKS_LIST")).append(System.lineSeparator());
        var books = bookRepository.findAll();
        books.forEach(book -> sb.append(bookConverter.convertToString(book)).append(System.lineSeparator()));
        return sb.toString();
    }

    @Override
    @Transactional
    public String insertBook(String title, long idAuthor, long idGenre) {
        var author = authorRepository.findById(idAuthor);
        var genre = genreRepository.findById(idGenre);
        if (author.isEmpty() || genre.isEmpty()) {
            return messageService.getMessage("ERR_INTEGRITY");
        }
        author.ifPresent(a -> genre.ifPresent(g -> {
            var book = new Book(0, title, a, g);
            bookRepository.save(book);
        }));
        return messageService.getMessage("CMD_COMPLETE");
    }

    @Override
    public String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        return null;
    }

    @Override
    public String deleteBook(long idBook) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookById(long idBook) {
        var book = bookRepository.getById(idBook);
        if (book != null) {
            return bookConverter.convertToString(book);
        } else {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }
}
