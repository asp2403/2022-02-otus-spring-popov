package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.dto.BookDto;
import ru.otus.homework.popov.service.localization.MessageService;

@Component
public class BookDtoConverterImpl implements BookDtoConverter {
    private final MessageService messageService;

    public BookDtoConverterImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String convertToString(BookDto bookDto) {
        return messageService.getMessage("BOOK_TEMPLATE", new Object[] {bookDto.getId(), bookDto.getAuthorName(), bookDto.getTitle(), bookDto.getGenreName(), bookDto.getCommentCount()});
    }
}
