package ru.otus.homework.popov.service;

import java.io.InputStream;
import java.io.PrintStream;

public interface IOProvider {
    PrintStream getOut();
    InputStream getIn();
}
