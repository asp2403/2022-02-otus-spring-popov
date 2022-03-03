package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class IOServiceImpl implements IOService{

    private final PrintStream output;

    public IOServiceImpl() {
        this.output = System.out;
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
