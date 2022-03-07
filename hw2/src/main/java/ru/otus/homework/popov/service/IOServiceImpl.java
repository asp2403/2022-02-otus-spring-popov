package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Function;

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
    public void println(String s) {
        output.println(s);
    }

    @Override
    public void printlnFormat(String s, Object... args) {
        println(String.format(s, args));
    }

    @Override
    public String readString(String prompt) {
        printPrompt(prompt);
        return scanner.nextLine().trim();
    }

    private void printPrompt(String prompt) {
        output.print(prompt);
    }

    @Override
    public String readNotEmptyString(String prompt, Function<Integer, String> getErrorMessage) {
        do {
            var s = readString(prompt);
            if (s.isEmpty()) {
                println(getErrorMessage.apply(ERR_BLANK_STRING));
            } else {
                return s;
            }
        } while(true);
    }

    @Override
    public String readNotEmptyString(String prompt, String description, Function<Integer, String> getErrorMessage) {
        println(description);
        return readNotEmptyString(prompt, getErrorMessage);
    }

    @Override
    public char readChar(String prompt, Function<Integer, String> getErrorMessage) {
        do {
            var s = readString(prompt).toLowerCase();
            if (s.length() != 1) {
                println(getErrorMessage.apply(ERR_CHAR_EXPECTED));
            } else {
                return s.charAt(0);
            }
        } while (true);
    }

}
