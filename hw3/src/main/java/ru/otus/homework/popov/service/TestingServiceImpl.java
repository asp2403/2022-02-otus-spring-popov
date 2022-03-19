package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.config.UISettings;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.TestingResult;
import ru.otus.homework.popov.domain.User;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;
    private final QuestionService questionService;
    private final QuestionConverter questionConverter;
    private final MessageService messageService;
    private final UISettings uiSettings;

    public TestingServiceImpl(QuestionService questionService,
                              IOService ioService,
                              QuestionConverter questionConverter,
                              MessageService messageService,
                              UISettings uiSettings) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.questionConverter = questionConverter;
        this.messageService = messageService;
        this.uiSettings = uiSettings;
    }

    private void askQuestion(Question q, TestingResult testingResult) {
        ioService.println(questionConverter.convertQuestionToString(q));
        ioService.printlnFormat(messageService.getMessage("MSG_QUESTION"), uiSettings.getCmdQuit());
        do {
            try {
                var ch = ioService.readChar(uiSettings.getPrompt(), messageService::getIOErrorMessage);
                if (ch == uiSettings.getCmdQuit()) {
                    testingResult.setAborted(true);
                    return;
                }
                var answerIndex = ch - 'a';
                var isCorrect = q.getAnswers().get(answerIndex).isCorrect();
                testingResult.applyAnswer(isCorrect);
                break;
            } catch (IndexOutOfBoundsException e) {
                ioService.println(messageService.getMessage("ERR_OUT_OF_BOUNDS"));
            }
        } while (true);
    }

    @Override
    public TestingResult testUser(User user) {
        var testingResult = new TestingResult(user);
        List<Question> questions = questionService.loadQuestions();
        for (var q : questions) {
            askQuestion(q, testingResult);
            if (testingResult.isAborted()) {
                break;
            }
        }
        return testingResult;
    }
}
