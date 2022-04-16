package ru.otus.homework.popov.service.converter;

import ru.otus.homework.popov.dto.BookDto;

public interface BookDtoConverter {
    String convertToString(BookDto bookDto);
}
