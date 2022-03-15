package ru.otus.homework.popov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    public MessageServiceImpl(AppConfig appConfig, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(appConfig.getLocale());
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String id) {
        return messageSource.getMessage(id, null, locale);
    }

    @Override
    public String getMessageFormat(String id, Object... args) {
        return String.format(getMessage(id), args);
    }

    @Override
    public String getIOErrorMessage(int errorCode) {
        switch (errorCode) {
            case IOService.ERR_BLANK_STRING:
                return getMessage("ERR_EMPTY_STRING");
            case IOService.ERR_CHAR_EXPECTED:
                return getMessage("ERR_ONE_CHAR_EXPECTED");
            default:
                return getMessage("ERR_UNKNOWN");
        }
    }
}
