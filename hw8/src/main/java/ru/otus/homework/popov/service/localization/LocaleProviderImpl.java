package ru.otus.homework.popov.service.localization;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleProviderImpl implements LocaleProvider {
    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }
}
