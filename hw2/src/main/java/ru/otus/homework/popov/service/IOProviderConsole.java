package ru.otus.homework.popov.service;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class IOProviderConsole implements IOProvider {

    private final InputStream in;
    private final PrintStream out;

    public IOProviderConsole() {
        this.in = System.in;
        this.out = System.out;
    }

    @Override
    public PrintStream getOut() {
        return out;
    }

    @Override
    public InputStream getIn() {
        return in;
    }
}
