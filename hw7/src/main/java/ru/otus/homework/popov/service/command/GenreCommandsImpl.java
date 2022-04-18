package ru.otus.homework.popov.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.popov.repository.GenreRepository;
import ru.otus.homework.popov.service.converter.GenreConverter;
import ru.otus.homework.popov.service.localization.MessageService;

@Service
public class GenreCommandsImpl implements GenreCommands {
    private final GenreRepository genreRepository;
    private final MessageService messageService;
    private final GenreConverter genreConverter;

    public GenreCommandsImpl(GenreRepository genreRepository, MessageService messageService, GenreConverter genreConverter) {
        this.genreRepository = genreRepository;
        this.messageService = messageService;
        this.genreConverter = genreConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public String getAll() {
        var sb = new StringBuilder(messageService.getMessage("GENRES_LIST")).append(System.lineSeparator());
        var genres = genreRepository.findAll();
        genres.forEach(genre -> sb.append(genreConverter.convertToString(genre)).append(System.lineSeparator()));
        return sb.toString();
    }
}