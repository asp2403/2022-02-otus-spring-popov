package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import ru.otus.homework.popov.config.LocalizationSettings;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class QuestionResourceNameProviderImplTest {

    @Autowired
    private QuestionResourceNameProvider questionResourceNameProvider;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private LocalizationSettings localizationSettings;

    @MockBean
    private LocaleProvider localeProvider;


    @DisplayName("должен корректно выдавать имя ресурса")
    @Test
    void shouldCorrectGetResourceName() {
        given(localeProvider.getLocale()).willReturn(Locale.forLanguageTag("ru-RU"));
        given(localizationSettings.getQuestionsBaseName()).willReturn("questions/test");
        assertEquals("classpath:questions/test_ru_RU.csv", questionResourceNameProvider.getResourceName());
    }



}