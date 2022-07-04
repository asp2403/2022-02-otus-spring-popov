package ru.otus.homework.popov.hw15.domain;

public class Cocktail {
    private final Receipt receipt;


    public Cocktail(Receipt receipt) {
        this.receipt = receipt;
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
