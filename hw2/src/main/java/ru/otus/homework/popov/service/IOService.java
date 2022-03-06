package ru.otus.homework.popov.service;

public interface IOService {

    void print(String s);

    void println(String s);

    String readString();

    String readString(String prompt);
}
