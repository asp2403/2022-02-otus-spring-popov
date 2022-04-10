package ru.otus.homework.popov.service.command;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.repository.AuthorRepository;
import ru.otus.homework.popov.service.converter.AuthorConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class AuthorCommandsImpl implements AuthorCommands {
    private final AuthorRepository authorRepository;
    private final MessageService messageService;
    private final AuthorConverter authorConverter;

    public AuthorCommandsImpl(AuthorRepository authorRepository, MessageService messageService, AuthorConverter authorConverter) {
        this.authorRepository = authorRepository;
        this.messageService = messageService;
        this.authorConverter = authorConverter;
    }

    @Override
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("AUTHORS_LIST")).append(System.lineSeparator());
        var authors = authorRepository.findAll();
        authors.forEach(author -> sb.append(authorConverter.convertToString(author)).append(System.lineSeparator()));
        return sb.toString();
    }
}
