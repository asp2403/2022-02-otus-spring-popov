package ru.otus.homework.popov.service;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ru.otus.homework.popov.config.LocalizationSettings;

import java.util.Locale;

@Component
public class QuestionResourceNameProviderImpl implements QuestionResourceNameProvider {
    private static final String EXT = ".csv";
    private static final String PREFIX = "classpath:";
    private final ResourceLoader resourceLoader;
    private final LocalizationSettings localizationSettings;
    private final LocaleProvider localeProvider;

    public QuestionResourceNameProviderImpl(ResourceLoader resourceLoader,
                                            LocalizationSettings localizationSettings,
                                            LocaleProvider localeProvider
                                            ) {
        this.resourceLoader = resourceLoader;
        this.localizationSettings = localizationSettings;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getResourceName() {
        var separator = "_";
        var locale = localeProvider.getLocale();
        var language = locale.getLanguage();
        var baseName = localizationSettings.getQuestionsBaseName();
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
