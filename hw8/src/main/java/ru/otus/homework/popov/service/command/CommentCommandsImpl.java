package ru.otus.homework.popov.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.service.converter.BookConverter;
import ru.otus.homework.popov.service.converter.CommentConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class CommentCommandsImpl implements CommentCommands {
    private final BookRepository bookRepository;
    private final CommentConverter commentConverter;
    private final MessageService messageService;
    private final BookConverter bookConverter;
    private final CommentRepository commentRepository;

    public CommentCommandsImpl(BookRepository bookRepository, CommentConverter commentConverter, MessageService messageService, BookConverter bookConverter, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentConverter = commentConverter;
        this.messageService = messageService;
        this.bookConverter = bookConverter;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public String getComments(String idBook) {
        var book = bookRepository.findById(idBook);
        var sb = new StringBuilder();
        book.ifPresentOrElse(b -> {
            sb.append(messageService.getMessage("COMMENT_LIST"));
            var comments = commentRepository.findByBookId(idBook);
            sb.append(System.lineSeparator())
                    .append(bookConverter.convertToString(b))
                    .append(System.lineSeparator())
                    .append("----------------------")
                    .append(System.lineSeparator());
            comments.forEach(comment -> sb.append(commentConverter.convertToString(comment)).append(System.lineSeparator()));
        }, () -> sb.append(messageService.getMessage("ERR_BOOK_NOT_FOUND")));
        return sb.toString();
    }

    @Override
    @Transactional
    public String addComment(String idBook, String text) {
        var book = bookRepository.findById(idBook);
        var sb = new StringBuilder();
        book.ifPresentOrElse(
                b -> {
                    var comment = new Comment(null, text);
                    b.addComment(comment);
                    commentRepository.save(comment);
                    bookRepository.save(b);
                    sb.append(messageService.getMessage("CMD_COMPLETE"));
                },
                () -> sb.append(messageService.getMessage("ERR_BOOK_NOT_FOUND"))
        );
        return sb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public String getComment(String idComment) {
        var sb = new StringBuilder();
        var comment = commentRepository.findById(idComment);
        comment.ifPresentOrElse(
                c -> {
                    var book = c.getBook();
                    sb.append(bookConverter.convertToString(book))
                                    .append(System.lineSeparator())
                                    .append(commentConverter.convertToString(c));
                },
                () -> sb.append(messageService.getMessage("ERR_COMMENT_NOT_FOUND"))
        );
        return sb.toString();
    }

    @Override
    @Transactional
    public String updateComment(String idComment, String text) {
        var comment = commentRepository.findById(idComment);
        var sb = new StringBuilder();
        comment.ifPresentOrElse(
                c -> {
                    c.setText(text);
                    commentRepository.save(c);
                    sb.append(messageService.getMessage("CMD_COMPLETE"));
                },
                () -> sb.append(messageService.getMessage("ERR_COMMENT_NOT_FOUND"))
        );
        return sb.toString();
    }

    @Override
    @Transactional
    public String deleteComment(String idComment) {
        var comment = commentRepository.findById(idComment);
        var sb = new StringBuilder();
        comment.ifPresentOrElse(
                c -> {
                    var book = c.getBook();
                    if (book != null) {
                        book.delComment(c);
                        bookRepository.save(book);
                    }
                    commentRepository.deleteById(idComment);
                    sb.append(messageService.getMessage("CMD_COMPLETE"));
                },
                () -> sb.append(messageService.getMessage("ERR_COMMENT_NOT_FOUND"))
        );
        return sb.toString();
    }
}
