package ru.otus.homework.popov.service.localization;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageSource messageSource;
    private final LocaleProvider localeProvider;

    public MessageServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getMessage(String id) {
        return messageSource.getMessage(id, null, localeProvider.getLocale());
    }

    @Override
    public String getMessage(String id, Object[] args) {
        return messageSource.getMessage(id, args, localeProvider.getLocale());
    }
}
