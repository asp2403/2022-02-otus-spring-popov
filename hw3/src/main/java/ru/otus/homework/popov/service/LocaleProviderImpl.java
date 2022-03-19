package ru.otus.homework.popov.service;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.config.LocalizationSettings;

import java.util.Locale;

@Component
public class LocaleProviderImpl implements LocaleProvider {
    private final Locale locale;

    public LocaleProviderImpl(LocalizationSettings localizationSettings) {
        locale = Locale.forLanguageTag(localizationSettings.getLocale());
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
