package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.exceptions.EmptyStringException;
import ru.otus.homework.popov.exceptions.NotCharException;

@Service
public class AppRunner {

    public static final char CMD_EXIT = 'q';
    public static final char CMD_YES = 'y';
    public static final char CMD_NO = 'n';
    public static final String MSG_WELCOME = String.format("Welcome to the test! Enter your name, please. Type '%s' for exit", CMD_EXIT);
    public static final String MSG_HELLO = "Hello, %s! You should score %d points to pass the test. Good luck!";
    public static final String MSG_QUESTION = String.format("Choose the correct answer or type '%s' for exit", CMD_EXIT);
    public static final String ERR_EMPTY_STRING = "String value can not be blank!";
    public static final String ERR_STRING_TOO_LONG = "You should type one character!";
    public static final String ERR_OUT_OF_BOUNDS = "Character is out of bounds!";
    public static final String MSG_SCORE = "Test complete. Your score is: %d";
    public static final String MSG_SUCCESS = "Congratulations, you successfully pass the test!";
    public static final String MSG_FAIL = "Sadly, you couldn't pass the test :(";
    public static final String MSG_TRY_AGAIN = String.format("Would you like to take the test one more time? Type '%s' to try again", CMD_YES);
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
        if (sayHello()) {
            boolean res;
            do {
                res = run();
            } while (res);
        }
        sayGoodBy();
    }

    private boolean processUserAnswer(int questionIndex) {
        var result = true;
        ioService.println(questionConverter.convertQuestionToString(questionIndex, testingService.getQuestion(questionIndex)));
        ioService.println(MSG_QUESTION);
        do {
            try {
                var ch = ioService.readChar(PROMPT);
                if (ch == CMD_EXIT) {
                    result = false;
                    break;
                }
                var answerIndex = ch - 'a';
                testingService.answerQuestion(questionIndex, answerIndex);
                break;
            } catch (NotCharException e) {
                ioService.println(ERR_STRING_TOO_LONG);
            } catch (IndexOutOfBoundsException e) {
                ioService.println(ERR_OUT_OF_BOUNDS);
            }
        } while (true);
        return result;
    }

    private void sayGoodBy() {
        ioService.println(MSG_GOOD_BY);
    }

    private boolean answerQuestions() {
        for (var index = 0; index < testingService.getQuestionCount(); index++) {
            if (!processUserAnswer(index)) {
                return false;
            }
        }
        return true;
    }


    private boolean run() {
        if (!answerQuestions()) {
            return false;
        }
        var score = testingService.getUser().getScore();
        ioService.printlnFormat(MSG_SCORE, score);
        if (score >= scoreToPass) {
            ioService.println(MSG_SUCCESS);
        } else {
            ioService.println(MSG_FAIL);
        }
        ioService.println(MSG_TRY_AGAIN);
        try {
            var ch = ioService.readChar(PROMPT);
            if (ch == CMD_YES) {
                testingService.tryAgain();
                return true;
            } else {
                return false;
            }
        } catch (NotCharException e) {
            return false;
        }

    }

    private boolean sayHello() {
        ioService.println(MSG_WELCOME);
        do {
            try {
                var userName = ioService.readString(PROMPT);
                if (userName.equalsIgnoreCase(Character.toString(CMD_EXIT))) {
                    return false;
                }
               testingService.registerUser(userName);
               ioService.printlnFormat(MSG_HELLO, userName, scoreToPass);
               return true;
            } catch (EmptyStringException e) {
                ioService.println(ERR_EMPTY_STRING);
            }
        } while (true);
    }
}