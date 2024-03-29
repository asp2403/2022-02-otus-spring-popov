package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.dao.BookDao;
import ru.otus.homework.popov.dao.CommentDao;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.service.converter.BookDtoConverter;
import ru.otus.homework.popov.service.converter.CommentConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class CommentCommandsImpl implements CommentCommands {
    private final CommentDao commentDao;
    private final BookDao bookDao;
    private final CommentConverter commentConverter;
    private final BookDtoConverter bookDtoConverter;
    private final MessageService messageService;

    public CommentCommandsImpl(CommentDao commentDao, BookDao bookDao, CommentConverter commentConverter, BookDtoConverter bookDtoConverter, MessageService messageService) {
        this.commentDao = commentDao;
        this.bookDao = bookDao;
        this.commentConverter = commentConverter;
        this.bookDtoConverter = bookDtoConverter;
        this.messageService = messageService;
    }

    @Override
    @Transactional(readOnly = true)
    public String getComments(long idBook) {
        try {
            var book = bookDao.getWithDetailsById(idBook);
            var comments = book.getComments();
            var bookDto = new BookDto(book, comments.size());
            var sb = new StringBuilder(messageService.getMessage("COMMENT_LIST"));
            sb.append(System.lineSeparator())
                    .append(bookDtoConverter.convertToString(bookDto))
                    .append(System.lineSeparator())
                    .append("----------------------")
                    .append(System.lineSeparator());
            comments.forEach(comment -> sb.append(commentConverter.convertToString(comment)).append(System.lineSeparator()));
            return sb.toString();
        } catch (Exception e) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }

    @Override
    @Transactional
    public String addComment(long idBook, String text) {
        var book = bookDao.getById(idBook);
        if (book == null) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
        var comment = new Comment(0, text, book);
        commentDao.save(comment);
        return messageService.getMessage("CMD_COMPLETE");

    }

    @Override
    @Transactional(readOnly = true)
    public String getComment(long idComment) {
        var comment = commentDao.getById(idComment);
        if (comment == null) {
            return messageService.getMessage("ERR_COMMENT_NOT_FOUND");
        }
        try {
            var bookDto = bookDao.getDtoById(comment.getBook().getId());
            return new StringBuilder().append(bookDtoConverter.convertToString(bookDto))
                    .append(System.lineSeparator())
                    .append(commentConverter.convertToString(comment)).toString();
        } catch (Exception e) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }

    @Override
    @Transactional
    public String updateComment(long idComment, String text) {
        var comment = commentDao.getById(idComment);
        if (comment == null) {
            return messageService.getMessage("ERR_COMMENT_NOT_FOUND");
        }
        comment.setText(text);
        commentDao.save(comment);
        return messageService.getMessage("CMD_COMPLETE");
    }

    @Override
    @Transactional
    public String deleteComment(long idComment) {
        commentDao.deleteById(idComment);
        return messageService.getMessage("CMD_COMPLETE");
    }
}
