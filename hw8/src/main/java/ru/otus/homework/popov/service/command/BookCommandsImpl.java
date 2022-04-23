package ru.otus.homework.popov.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.repository.GenreRepository;
import ru.otus.homework.popov.service.converter.BookConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class BookCommandsImpl implements BookCommands {
    private final MessageService messageService;
    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public BookCommandsImpl(MessageService messageService, BookRepository bookRepository, BookConverter bookConverter, AuthorRepository authorRepository, GenreRepository genreRepository, CommentRepository commentRepository) {
        this.messageService = messageService;
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
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
    public String insertBook(String title, String idAuthor, String idGenre) {
        var author = authorRepository.findById(idAuthor);
        var genre = genreRepository.findById(idGenre);
        if (author.isEmpty() || genre.isEmpty()) {
            return messageService.getMessage("ERR_INTEGRITY");
        }
        var book = new Book(title, author.get(), genre.get());
        bookRepository.save(book);
        return messageService.getMessage("CMD_COMPLETE");
    }

    @Override
    @Transactional
    public String updateBook(String idBook, String title, String idAuthor, String idGenre) {
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
    @Transactional
    public String deleteBook(String idBook) {
        var book = bookRepository.findById(idBook);
        var sb = new StringBuilder();
        book.ifPresentOrElse(
                b -> {
                    commentRepository.deleteByBookId(idBook);
                    bookRepository.deleteById(idBook);
                    sb.append(messageService.getMessage("CMD_COMPLETE"));
                },
                () -> sb.append(messageService.getMessage("ERR_BOOK_NOT_FOUND"))
        );
        return sb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public String getBookById(String idBook) {
        var book = bookRepository.findById(idBook);
        var s = book.map(b -> bookConverter.convertToString(b)).orElse(messageService.getMessage("ERR_BOOK_NOT_FOUND"));
        return s;
    }
}
