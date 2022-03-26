package ru.otus.homework.popov.service;

public interface MessagePrinter {
    void println(String MessageId);
    void printlnFormat(String MessageId, Object... args);
}
