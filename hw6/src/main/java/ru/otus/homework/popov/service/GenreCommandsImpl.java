package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.dao.GenreDao;
import ru.otus.homework.popov.service.converter.GenreConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class GenreCommandsImpl implements GenreCommands {
    private final GenreDao genreDao;
    private final MessageService messageService;
    private final GenreConverter genreConverter;

    public GenreCommandsImpl(GenreDao genreDao, MessageService messageService, GenreConverter genreConverter) {
        this.genreDao = genreDao;
        this.messageService = messageService;
        this.genreConverter = genreConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("GENRES_LIST")).append(System.lineSeparator());
        var genres = genreDao.getAll();
        genres.forEach(genre -> sb.append(genreConverter.convertToString(genre)).append(System.lineSeparator()));
        return sb.toString();
    }
}
