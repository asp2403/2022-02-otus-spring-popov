package ru.otus.homework.popov.service;

import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class QuestionResourceNameProviderImpl implements QuestionResourceNameProvider {
    private static final String EXT = ".csv";
    private static final String PREFIX = "classpath:";

    @Override
    public String getDefaultResourceName(String baseName) {
        return PREFIX + baseName + EXT;
    }

    @Override
    public String getResourceName(String baseName, Locale locale) {
        var separator = "_";
        var language = locale.getLanguage();
        if (language.isEmpty()) {
            return getDefaultResourceName(baseName);
        }
        var country = locale.getCountry();
        return PREFIX + baseName + separator + language + separator + country + EXT;
    }
}
