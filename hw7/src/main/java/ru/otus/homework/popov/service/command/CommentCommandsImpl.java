package ru.otus.homework.popov.service.command;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.projection.BookCommentCountProjection;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;
import ru.otus.homework.popov.service.converter.BookCommentCountProjectionConverter;
import ru.otus.homework.popov.service.converter.CommentConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class CommentCommandsImpl implements CommentCommands {
    private final BookRepository bookRepository;
    private final MessageService messageService;
    private final BookCommentCountProjectionConverter bookProjConverter;
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;

    public CommentCommandsImpl(BookRepository bookRepository, MessageService messageService, BookCommentCountProjectionConverter bookProjConverter, CommentConverter commentConverter, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.messageService = messageService;
        this.bookProjConverter = bookProjConverter;
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public String getComments(long idBook) {
        var book = bookRepository.findWithDetailsById(idBook);
        var sb = new StringBuilder();
        book.ifPresentOrElse(b -> {
            sb.append(messageService.getMessage("COMMENT_LIST"));
            var comments = b.getComments();
            var bookProj = new BookCommentCountProjection(b, comments.size());
            sb.append(System.lineSeparator())
                    .append(bookProjConverter.convertToString(bookProj))
                    .append(System.lineSeparator())
                    .append("----------------------")
                    .append(System.lineSeparator());
            comments.forEach(comment -> sb.append(commentConverter.convertToString(comment)).append(System.lineSeparator()));
        }, () -> sb.append(messageService.getMessage("ERR_BOOK_NOT_FOUND")));
        return sb.toString();
    }

    @Override
    @Transactional
    public String addComment(long idBook, String text) {
        var book = bookRepository.findById(idBook);
        var sb = new StringBuilder();
        book.ifPresentOrElse(
                b -> {
                    var comment = new Comment(0, text, b);
                    commentRepository.save(comment);
                    sb.append(messageService.getMessage("CMD_COMPLETE"));
                },
                () -> sb.append(messageService.getMessage("ERR_BOOK_NOT_FOUND"))
        );
        return sb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public String getComment(long idComment) {
        var comment = commentRepository.findById(idComment);
        var sb = new StringBuilder();
        comment.ifPresentOrElse(
                c -> {
                    var bookProj = bookRepository.findCommentCountProjectionById(c.getBook().getId());
                    bookProj.ifPresentOrElse(
                            bp -> {
                                sb.append(bookProjConverter.convertToString(bp))
                                        .append(System.lineSeparator())
                                        .append(commentConverter.convertToString(c));
                            },
                            () -> sb.append(messageService.getMessage("ERR_BOOK_NOT_FOUND"))
                    );

                },
                () -> sb.append(messageService.getMessage("ERR_COMMENT_NOT_FOUND"))
        );
        return sb.toString();
    }

    @Override
    @Transactional
    public String updateComment(long idComment, String text) {
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
    //@Transactional
    public String deleteComment(long idComment) {
        try {
            commentRepository.deleteById(idComment);
            return messageService.getMessage("CMD_COMPLETE");
        } catch (EmptyResultDataAccessException e) {
            return messageService.getMessage("ERR_COMMENT_NOT_FOUND");
        }
    }
}
