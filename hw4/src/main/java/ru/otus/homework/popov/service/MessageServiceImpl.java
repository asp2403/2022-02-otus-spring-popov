package ru.otus.homework.popov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final LocaleProvider localeProvider;
    private final MessageSource messageSource;

    public MessageServiceImpl(LocaleProvider localeProvider, MessageSource messageSource) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String id) {
        return messageSource.getMessage(id, null, localeProvider.getLocale());
    }

    @Override
    public String getMessageFormat(String id, Object... args) {
        return String.format(getMessage(id), args);
    }

}
