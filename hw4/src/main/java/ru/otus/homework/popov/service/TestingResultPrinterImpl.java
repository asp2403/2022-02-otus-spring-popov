package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.config.TestingSettings;
import ru.otus.homework.popov.domain.TestingResult;

@Service
public class TestingResultPrinterImpl implements TestingResultPrinter {

    private final TestingSettings testingSettings;
    private final MessagePrinter messagePrinter;

    public TestingResultPrinterImpl(MessagePrinter messagePrinter, TestingSettings testingSettings) {
        this.testingSettings = testingSettings;
        this.messagePrinter = messagePrinter;
    }

    @Override
    public void print(TestingResult res) {
        var score = res.getScore();
        messagePrinter.printlnFormat("MSG_SCORE", res.getUser().getFullName(), score);
        if (score >= testingSettings.getScoreToPass()) {
            messagePrinter.println("MSG_SUCCESS");
        } else {
            messagePrinter.println("MSG_FAIL");
        }
    }
}
