package ru.otus.homework.popov.hw5.service.command;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw5.dao.AuthorDao;
import ru.otus.homework.popov.hw5.service.converter.AuthorConverter;
import ru.otus.homework.popov.hw5.service.localization.MessageService;

@Service
public class GetAllAuthorsCommandImpl implements GetAllAuthorsCommand {
    private final MessageService messageService;
    private final AuthorDao authorDao;
    private final AuthorConverter authorConverter;

    public GetAllAuthorsCommandImpl(MessageService messageService, AuthorDao authorDao, AuthorConverter authorConverter) {
        this.messageService = messageService;
        this.authorDao = authorDao;
        this.authorConverter = authorConverter;
    }

    @Override
    public String execute() {
        var sb = new StringBuilder(messageService.getMessage("AUTHORS_LIST")).append(System.lineSeparator());
        var authors = authorDao.getAll();
        authors.forEach(author -> sb.append(authorConverter.convertToString(author)).append(System.lineSeparator()));
        return sb.toString();
    }
}
