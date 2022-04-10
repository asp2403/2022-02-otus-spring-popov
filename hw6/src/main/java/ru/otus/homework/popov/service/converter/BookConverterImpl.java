package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.dao.CommentDao;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.service.localization.MessageService;

import java.util.List;

@Component
public class BookConverterImpl implements BookConverter {

    private final MessageService messageService;
    private final CommentDao commentDao;

    public BookConverterImpl(MessageService messageService, CommentDao commentDao) {
        this.messageService = messageService;
        this.commentDao = commentDao;
    }

    @Override
    public String convertToString(Book book) {
        var commentCount = commentDao.getCountByBook(book);
        return convertToString(book, commentCount);

    }

    @Override
    public String convertToString(Book book, long commentCount) {
        return messageService.getMessage("BOOK_TEMPLATE", new Object[] {book.getId(), book.getAuthor().getName(), book.getTitle(), book.getGenre().getName(), commentCount});
    }

}
