package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.TestingResult;

@Service
public class TestingResultPrinterImpl implements TestingResultPrinter {

    private final IOService ioService;
    private final AppConfig appConfig;
    private final MessageService messageService;

    public TestingResultPrinterImpl(IOService ioService, AppConfig appConfig, MessageService messageService) {
        this.ioService = ioService;
        this.appConfig = appConfig;
        this.messageService = messageService;
    }

    @Override
    public void print(TestingResult res) {
        var score = res.getScore();
        ioService.printlnFormat(messageService.getMessage("MSG_SCORE"), res.getUser().getFullName(), score);
        if (score >= appConfig.getScoreToPass()) {
            ioService.println(messageService.getMessage("MSG_SUCCESS"));
        } else {
            ioService.println(messageService.getMessage("MSG_FAIL"));
        }
    }
}
