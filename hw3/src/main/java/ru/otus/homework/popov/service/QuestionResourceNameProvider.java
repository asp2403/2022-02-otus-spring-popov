package ru.otus.homework.popov.service;

import java.util.Locale;

public interface QuestionResourceNameProvider {
    String getDefaultResourceName(String baseName);
    String getResourceName(String baseName, Locale locale);
}
