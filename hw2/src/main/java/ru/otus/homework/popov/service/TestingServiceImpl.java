package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.TestingResult;
import ru.otus.homework.popov.domain.User;

import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {

    private final IOService ioService;
    private final QuestionService questionService;
    private final QuestionConverter questionConverter;

    public TestingServiceImpl(QuestionService questionService, IOService ioService, QuestionConverter questionConverter) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.questionConverter = questionConverter;
    }

    private void askQuestion(Question q, TestingResult testingResult) {
        ioService.println(questionConverter.convertQuestionToString(q));
        ioService.println(Messages.MSG_QUESTION);
        do {
            try {
                var ch = ioService.readChar(Messages.PROMPT, Messages::getIOErrorMessage);
                if (ch == Messages.CMD_EXIT) {
                    testingResult.setAborted(true);
                    return;
                }
                var answerIndex = ch - 'a';
                var isCorrect = q.getAnswers().get(answerIndex).isCorrect();
                testingResult.applyAnswer(isCorrect);
                break;
            } catch (IndexOutOfBoundsException e) {
                ioService.println(Messages.ERR_OUT_OF_BOUNDS);
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
