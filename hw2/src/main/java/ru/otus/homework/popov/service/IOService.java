package ru.otus.homework.popov.service;

public interface IOService {

    void print(String s);

    void printFormat(String s, Object... args);

    void println(String s);

    void printlnFormat(String s, Object... args);

    String readString();

    String readString(String prompt);

    char readChar();

    char readChar(String prompt);
}
