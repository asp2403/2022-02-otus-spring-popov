package ru.otus.homework.popov.service;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class QuestionResourceNameProviderImpl implements QuestionResourceNameProvider {
    private static final String EXT = ".csv";
    private static final String PREFIX = "classpath:";
    private final ResourceLoader resourceLoader;

    public QuestionResourceNameProviderImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String getResourceName(String baseName, Locale locale) {
        var separator = "_";
        var language = locale.getLanguage();
        var defaultResourceName = getDefaultResourceName(baseName);
        if (language.isEmpty()) {
            return defaultResourceName;
        }
        var country = locale.getCountry();
        var resourceName = PREFIX + baseName + separator + language + separator + country + EXT;
        var resource = resourceLoader.getResource(resourceName);
        if (!resource.exists()) {
            return defaultResourceName;
        } else {
            return resourceName;
        }
    }

    private String getDefaultResourceName(String baseName) {
        return PREFIX + baseName + EXT;
    }
}
