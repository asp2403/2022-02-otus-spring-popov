package ru.otus.homework.popov.service;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleProviderImpl implements LocaleProvider {
    private final Locale locale;

    public LocaleProviderImpl(AppConfig appConfig) {
        locale = Locale.forLanguageTag(appConfig.getLocale());
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
