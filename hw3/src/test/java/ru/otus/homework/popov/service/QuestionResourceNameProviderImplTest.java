package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class QuestionResourceNameProviderImplTest {

    private QuestionResourceNameProvider questionResourceNameProvider;

    @BeforeEach
    void setUp() {
        questionResourceNameProvider = new QuestionResourceNameProviderImpl();
    }

    @DisplayName("должен корректно выдавать имя ресурса по умолчанию")
    @Test
    void shouldCorrectGetDefaultResourceName() {
        assertEquals("classpath:test.csv", questionResourceNameProvider.getDefaultResourceName("test"));
    }

    @DisplayName("должен корректно выдавать имя ресурса")
    @Test
    void shouldCorrectGetResourceName() {
        var localeRu = Locale.forLanguageTag("ru-RU");
        var localeDef = Locale.forLanguageTag("");
        assertAll(
                () -> assertEquals("classpath:test_ru_RU.csv", questionResourceNameProvider.getResourceName("test", localeRu)),
                () -> assertEquals("classpath:test.csv", questionResourceNameProvider.getResourceName("test", localeDef))
        );
    }



}