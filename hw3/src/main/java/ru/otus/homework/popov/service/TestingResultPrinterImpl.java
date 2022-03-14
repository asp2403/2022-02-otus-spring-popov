package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.TestingResult;

@Service
public class TestingResultPrinterImpl implements TestingResultPrinter {

    private final IOService ioService;
    private final AppConfig appConfig;

    public TestingResultPrinterImpl(IOService ioService, AppConfig appConfig) {
        this.ioService = ioService;
        this.appConfig = appConfig;
    }

    @Override
    public void print(TestingResult res) {
        var score = res.getScore();
        ioService.printlnFormat(Messages.MSG_SCORE, res.getUser().getFullName(), score);
        if (score >= appConfig.getScoreToPass()) {
            ioService.println(Messages.MSG_SUCCESS);
        } else {
            ioService.println(Messages.MSG_FAIL);
        }
    }
}
