package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.projection.BookCommentCountProjection;
import ru.otus.homework.popov.service.localization.MessageService;

import java.util.Optional;

@Component
public class BookCommentCountProjectionConverterImpl implements BookCommentCountProjectionConverter {
    private final MessageService messageService;

    public BookCommentCountProjectionConverterImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String convertToString(BookCommentCountProjection bookCommentCountProjection) {
        return messageService.getMessage("BOOK_TEMPLATE", new Object[] {bookCommentCountProjection.getId(), bookCommentCountProjection.getAuthorName(), bookCommentCountProjection.getTitle(), bookCommentCountProjection.getGenreName(), bookCommentCountProjection.getCommentCount()});
    }
}
