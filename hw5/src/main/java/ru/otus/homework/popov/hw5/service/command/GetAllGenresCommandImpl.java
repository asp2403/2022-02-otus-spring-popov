package ru.otus.homework.popov.hw5.service.command;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.hw5.dao.GenreDao;
import ru.otus.homework.popov.hw5.service.converter.GenreConverter;
import ru.otus.homework.popov.hw5.service.localization.MessageService;

@Service
public class GetAllGenresCommandImpl implements GetAllGenresCommand {
    private final MessageService messageService;
    private final GenreDao genreDao;
    private final GenreConverter genreConverter;

    public GetAllGenresCommandImpl(MessageService messageService, GenreDao genreDao, GenreConverter genreConverter) {
        this.messageService = messageService;
        this.genreDao = genreDao;
        this.genreConverter = genreConverter;
    }

    @Override
    public String execute() {
        var sb = new StringBuilder(messageService.getMessage("GENRES_LIST")).append(System.lineSeparator());
        var genres = genreDao.getAll();
        genres.forEach(genre -> sb.append(genreConverter.convertToString(genre)).append(System.lineSeparator()));
        return sb.toString();
    }
}
