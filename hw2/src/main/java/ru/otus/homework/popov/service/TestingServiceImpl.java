package ru.otus.homework.popov.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.popov.dao.QuestionDao;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.TestingResult;
import ru.otus.homework.popov.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private boolean askQuestion(Question q, AtomicBoolean isTerminated) {
        ioService.println(questionConverter.convertQuestionToString(q));
        ioService.println(Messages.MSG_QUESTION);
        do {
            try {
                var ch = ioService.readChar(Messages.PROMPT, Messages::getIOErrorMessage);
                if (ch == Messages.CMD_EXIT) {
                    isTerminated.set(true);
                    return false;
                }
                var answerIndex = ch - 'a';
                return q.getAnswers().get(answerIndex).isCorrect();
            } catch (IndexOutOfBoundsException e) {
                ioService.println(Messages.ERR_OUT_OF_BOUNDS);
            }
        } while (true);
    }

    @Override
    public TestingResult testUser(User user, AtomicBoolean isTerminated) {
        var testingResult = new TestingResult(user);
        List<Question> questions = questionService.loadQuestions();
        for (var q : questions) {
            var answerResult = askQuestion(q, isTerminated);
            if (!isTerminated.get()) {
                testingResult.applyAnswer(answerResult);
            } else {
                return null;
            }
        }
        return testingResult;
    }
}
