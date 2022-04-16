package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.dao.AuthorDao;
import ru.otus.homework.popov.service.converter.AuthorConverter;
import ru.otus.homework.popov.service.localization.MessageService;



@Service
public class AuthorCommandsImpl implements AuthorCommands {
    private final AuthorDao authorDao;
    private final AuthorConverter authorConverter;
    private final MessageService messageService;

    public AuthorCommandsImpl(AuthorDao authorDao, AuthorConverter authorConverter, MessageService messageService) {
        this.authorDao = authorDao;
        this.authorConverter = authorConverter;
        this.messageService = messageService;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("AUTHORS_LIST")).append(System.lineSeparator());
        var authors = authorDao.getAll();
        authors.forEach(author -> sb.append(authorConverter.convertToString(author)).append(System.lineSeparator()));
        return sb.toString();
    }
}
