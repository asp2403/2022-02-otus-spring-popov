package ru.otus.homework.popov.service;

import java.util.function.Function;

public interface IOService {

    int ERR_BLANK_STRING = 1;
    int ERR_CHAR_EXPECTED = 2;

    void print(String s);

    void println(String s);

    String readString(String prompt);

    String readNotEmptyString(String prompt, Function<Integer, String> getErrorMessage);

    String readNotEmptyString(String prompt, String description, Function<Integer, String> getErrorMessage);

    char readChar(String prompt, Function<Integer, String> getErrorMessage);
}
