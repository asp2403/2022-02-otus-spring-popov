package ru.otus.homework.popov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionResourceNameProviderImplTest {

    @Autowired
    private QuestionResourceNameProvider questionResourceNameProvider;

    @Autowired
    private ResourceLoader resourceLoader;

    @DisplayName("должен корректно выдавать имя ресурса")
    @Test
    void shouldCorrectGetResourceName() {
        var localeRu = Locale.forLanguageTag("ru-RU");
        var localeDef = Locale.forLanguageTag("");
        var baseName = "questions/test";
        assertAll(
                () -> assertEquals("classpath:questions/test_ru_RU.csv", questionResourceNameProvider.getResourceName(baseName, localeRu)),
                () -> assertEquals("classpath:questions/test.csv", questionResourceNameProvider.getResourceName(baseName, localeDef))
        );
    }



}