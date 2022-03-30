package ru.otus.homework.popov.hw5.service.command;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw5.dao.BookDao;
import ru.otus.homework.popov.hw5.service.localization.MessageService;

@Service
public class DeleteBookByIdCommandImpl implements DeleteBookByIdCommand {
    private final BookDao bookDao;
    private final MessageService messageService;

    public DeleteBookByIdCommandImpl(BookDao bookDao, MessageService messageService) {
        this.bookDao = bookDao;
        this.messageService = messageService;
    }

    @Override
    public String execute(long id) {
        bookDao.deleteById(id);
        return messageService.getMessage("CMD_COMPLETE");
    }
}
