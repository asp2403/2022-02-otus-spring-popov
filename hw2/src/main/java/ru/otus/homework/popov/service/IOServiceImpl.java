package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.exceptions.EmptyStringException;
import ru.otus.homework.popov.exceptions.NotCharException;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService{

    private final PrintStream output;
    private final Scanner scanner;

    public IOServiceImpl() {

        this.output = System.out;
        scanner = new Scanner(System.in);
    }

    @Override
    public void print(String s) {
        output.print(s);
    }

    @Override
    public void printFormat(String s, Object... args) {
        print(String.format(s, args));
    }

    @Override
    public void println(String s) {
        output.println(s);
    }

    @Override
    public void printlnFormat(String s, Object... args) {
        println(String.format(s, args));
    }

    private void printPrompt(String prompt) {
        output.print(prompt);
    }

    @Override
    public String readString() {
        var s = scanner.nextLine().trim();
        if (s.isEmpty()) {
            throw new EmptyStringException("String is empty!");
        }
        return s;
    }

    @Override
    public String readString(String prompt) {
        printPrompt(prompt);
        return readString();
    }

    @Override
    public char readChar() {
        var s = scanner.nextLine().trim().toLowerCase();
        if (s.length() != 1) {
            throw new NotCharException("Not a char!");
        }
        return s.charAt(0);
    }

    @Override
    public char readChar(String prompt) {
        printPrompt(prompt);
        return readChar();
    }
}
