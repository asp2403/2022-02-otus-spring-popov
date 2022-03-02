package ru.otus.homework.popov.service;

import ru.otus.homework.popov.domain.Question;

import java.util.List;

public class AppRunner {

    private final QuestionService questionService;
    private final IOService ioService;
    private final QuestionConverter questionConverter;

    public AppRunner(IOService ioService, QuestionConverter questionConverter, QuestionService questionService) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.questionConverter = questionConverter;
    }

    public void run() {
        List<Question> list = questionService.getQuestions();
        for (var i = 0; i < list.size(); i ++) {
            ioService.outputString(questionConverter.convertQuestionToString(i, list.get(i)));
        }
    }
}
