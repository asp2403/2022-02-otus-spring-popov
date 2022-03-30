package ru.otus.homework.popov.hw5.service.command;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw5.dao.BookDao;
import ru.otus.homework.popov.hw5.service.converter.BookConverter;
import ru.otus.homework.popov.hw5.service.localization.MessageService;

@Service
public class GetBookByIdCommandImpl implements GetBookByIdCommand {
    private final BookDao bookDao;
    private final BookConverter bookConverter;
    private final MessageService messageService;

    public GetBookByIdCommandImpl(BookDao bookDao, BookConverter bookConverter, MessageService messageService) {
        this.bookDao = bookDao;
        this.bookConverter = bookConverter;
        this.messageService = messageService;
    }

    @Override
    public String execute(long id) {
        try {
            var book = bookDao.getById(id);
            return bookConverter.convertToString(book);
        } catch (EmptyResultDataAccessException e) {
            return messageService.getMessage("ERR_BOOK_NOT_FOUND");
        }
    }
}
