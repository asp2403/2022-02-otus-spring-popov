package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.service.localization.MessageService;

@Component
public class BookConverterImpl implements BookConverter {
    private final MessageService messageService;

    public BookConverterImpl(MessageService messageService) {
        this.messageService = messageService;
    }


    @Override
    public String convertToString(Book book) {
        return messageService.getMessage("BOOK_TEMPLATE", new Object[] {book.getId(), book.getAuthor().getName(), book.getTitle(), book.getGenre().getName(), book.getCommentCount()});

    }
}
