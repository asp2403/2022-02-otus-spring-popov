package ru.otus.homework.popov.service.command;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.GenreRepository;
import ru.otus.homework.popov.service.converter.BookCommentCountProjectionConverter;
import ru.otus.homework.popov.service.localization.MessageService;


@Service
public class BookCommandsImpl implements BookCommands {

    private final MessageService messageService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookCommentCountProjectionConverter bookCommentCountProjectionConverter;

    public BookCommandsImpl(MessageService messageService, BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, BookCommentCountProjectionConverter bookCommentCountProjectionConverter) {

        this.messageService = messageService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookCommentCountProjectionConverter = bookCommentCountProjectionConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("BOOKS_LIST")).append(System.lineSeparator());
        var books = bookRepository.findCommentCountProjectionAll();
        books.forEach(book -> sb.append(bookCommentCountProjectionConverter.convertToString(book)).append(System.lineSeparator()));
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
        var book = new Book(0, title, author.get(), genre.get());
        bookRepository.save(book);
        return messageService.getMessage("CMD_COMPLETE");
    }

    @Override
    @Transactional
    public String updateBook(long idBook, String title, long idAuthor, long idGenre) {
        var book = bookRepository.findById(idBook);
        if (book.isEmpty()) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
        var author = authorRepository.findById(idAuthor);
        var genre = genreRepository.findById(idGenre);
        if (author.isPresent() && genre.isPresent()) {
            book.ifPresent(b -> {
                b.setTitle(title);
                b.setAuthor(author.get());
                b.setGenre(genre.get());
                bookRepository.save(b);
            });
            return messageService.getMessage("CMD_COMPLETE");
        } else {
            return messageService.getMessage("ERR_INTEGRITY");
        }
    }

    @Override
    //@Transactional
    public String deleteBook(long idBook) {
        try {
            bookRepository.deleteById(idBook);
            return messageService.getMessage("CMD_COMPLETE");
        } catch (EmptyResultDataAccessException e) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookById(long idBook) {
        var book = bookRepository.findCommentCountProjectionById(idBook);
        var s = book.map(b -> bookCommentCountProjectionConverter.convertToString(b)).orElse(messageService.getMessage("ERR_BOOK_NOT_FOUND"));
        return s;
    }
}
