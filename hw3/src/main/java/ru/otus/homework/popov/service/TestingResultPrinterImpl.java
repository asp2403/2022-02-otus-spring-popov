package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.config.TestingSettings;
import ru.otus.homework.popov.domain.TestingResult;

@Service
public class TestingResultPrinterImpl implements TestingResultPrinter {

    private final IOService ioService;
    private final TestingSettings testingSettings;
    private final MessageService messageService;

    public TestingResultPrinterImpl(IOService ioService, TestingSettings testingSettings, MessageService messageService) {
        this.ioService = ioService;
        this.testingSettings = testingSettings;
        this.messageService = messageService;
    }

    @Override
    public void print(TestingResult res) {
        var score = res.getScore();
        ioService.printlnFormat(messageService.getMessage("MSG_SCORE"), res.getUser().getFullName(), score);
        if (score >= testingSettings.getScoreToPass()) {
            ioService.println(messageService.getMessage("MSG_SUCCESS"));
        } else {
            ioService.println(messageService.getMessage("MSG_FAIL"));
        }
    }
}
