package ru.otus.homework.popov.service;

import java.util.Locale;

public interface QuestionResourceNameProvider {
    String getResourceName(String baseName, Locale locale);
}
