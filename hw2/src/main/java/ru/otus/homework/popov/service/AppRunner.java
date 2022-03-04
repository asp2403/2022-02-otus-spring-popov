package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

import java.util.List;
@Service
public class AppRunner {

    public static final String CMD_EXIT = "q";
    public static final String CMD_YES = "y";
    public static final String MSG_WELCOME = String.format("Welcome to the test! Enter your name, please. Type '%s' for exit", CMD_EXIT);
    public static final String MSG_HELLO = "Hello, %s! You should score %d points to pass the test. Good luck!";
    public static final String MSG_QUESTION = String.format("Choose the correct answer or type '%s' for exit", CMD_EXIT);
    public static final String ERR_EMPTY_STRING = "String value can not be blank!";
    public static final String ERR_STRING_TOO_LONG = "You should type one character!";
    public static final String ERR_OUT_OF_BOUNDS = "Character is out of bounds!";
    public static final String MSG_SCORE = "Test complete. Your score is: %d";
    public static final String MSG_SUCCESS = "Congratulations, you successfully pass the test!";
    public static final String MSG_FAIL = String.format("Sadly you couldn't pass the test. Type '%s' to try again or any symbol for exit", CMD_YES);
    public static final String MSG_GOOD_BY = "Good by!";
    public static final String PROMPT = ">";

    private final int scoreToPass;
    private final TestingService testingService;
    private final IOService ioService;
    private final QuestionConverter questionConverter;


    public AppRunner(
            @Value("${scoreToPass}") int scoreToPass,
            IOService ioService,
            QuestionConverter questionConverter,
            TestingService testingService) {
        this.scoreToPass = scoreToPass;
        this.ioService = ioService;
        this.testingService = testingService;
        this.questionConverter = questionConverter;
    }

    public void execute() {
        if (!sayHello()) {
            sayGoodBy();
            return;
        }
        run();
    }

    private boolean processUserAnswer(int questionIndex) {
        var result = true;
        ioService.println(questionConverter.convertQuestionToString(questionIndex, testingService.getQuestion(questionIndex)));
        ioService.println(MSG_QUESTION);
        do {
            var s = ioService.readString(PROMPT).toLowerCase();
            if (s.equals(CMD_EXIT)) {
                result = false;
                break;
            }
            if (s.length() != 1) {
                ioService.println(ERR_STRING_TOO_LONG);
            } else {
                var answerIndex = s.charAt(0) - 'a';
                try {
                    testingService.answerQuestion(questionIndex, answerIndex);
                    break;
                } catch (IndexOutOfBoundsException e) {
                    ioService.println(ERR_OUT_OF_BOUNDS);
                }
            }
        } while (true);
        return result;
    }

    private void sayGoodBy() {
        ioService.println(MSG_GOOD_BY);
    }

    private void run() {
        for (var index = 0; index < testingService.getQuestionCount(); index++) {
            if (!processUserAnswer(index)) {
                sayGoodBy();
                return;
            }
        }
        var score = testingService.getUser().getScore();
        ioService.println(String.format(MSG_SCORE, score));
        if (score >= scoreToPass) {
            ioService.println(MSG_SUCCESS);
            sayGoodBy();
        } else {
            ioService.println(MSG_FAIL);
            var s = ioService.readString(PROMPT);
            if (s.toLowerCase().equals(CMD_YES)) {
                testingService.tryAgain();
                run();
            } else {
                sayGoodBy();
            }
        }

    }

    private boolean sayHello() {
        ioService.println(MSG_WELCOME);
        do {
            var userName = ioService.readString(PROMPT);
            if (userName.toLowerCase().equals(CMD_EXIT)) {
                return false;
            }
            if (userName.isEmpty()) {
                ioService.println(ERR_EMPTY_STRING);
            } else {
                testingService.registerUser(userName);
                ioService.println(String.format(MSG_HELLO, userName, scoreToPass));
                return true;
            }
        } while (true);
    }
}
