package ru.otus.homework.popov.service;

public interface IOService {

    void print(String s);

    void println(String s);

    int readInt();

    int readInt(String prompt);

    String readString();

    String readString(String prompt);
}
