package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
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
    public void println(String s) {
        output.println(s);
    }

    private void printPrompt(String prompt) {
        output.print(prompt);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public int readInt(String prompt) {
        printPrompt(prompt);
        return readInt();
    }

    @Override
    public String readString() {
        return scanner.nextLine().trim();
    }

    @Override
    public String readString(String prompt) {
        printPrompt(prompt);
        return readString();
    }
}
