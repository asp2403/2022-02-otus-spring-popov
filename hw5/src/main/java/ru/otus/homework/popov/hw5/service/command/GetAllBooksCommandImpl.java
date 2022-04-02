package ru.otus.homework.popov.hw5.service.command;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw5.dao.BookDao;
import ru.otus.homework.popov.hw5.service.converter.BookConverter;
import ru.otus.homework.popov.hw5.service.localization.MessageService;

@Service
public class GetAllBooksCommandImpl implements GetAllBooksCommand {
    private final MessageService messageService;
    private final BookDao bookDao;
    private final BookConverter bookConverter;

    public GetAllBooksCommandImpl(MessageService messageService, BookDao bookDao, BookConverter bookConverter) {
        this.messageService = messageService;
        this.bookDao = bookDao;
        this.bookConverter = bookConverter;
    }

    @Override
    public String execute() {
        var sb = new StringBuilder(messageService.getMessage("BOOKS_LIST")).append(System.lineSeparator());
        var books = bookDao.getAll();
        books.forEach(genre -> sb.append(bookConverter.convertToString(genre)).append(System.lineSeparator()));
        return sb.toString();
    }
}
