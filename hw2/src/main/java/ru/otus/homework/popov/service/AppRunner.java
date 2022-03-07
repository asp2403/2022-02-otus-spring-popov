package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

@Service
public class AppRunner {

    public static final char CMD_EXIT = 'q';
    public static final char CMD_YES = 'y';
    public static final String MSG_WELCOME = "Welcome to the test!";
    public static final String MSG_ENTER_NAME = String.format("Enter your name or type '%s' for exit", CMD_EXIT);
    public static final String MSG_ENTER_SURNAME = String.format("Enter your surname or type '%s' for exit", CMD_EXIT);
    public static final String MSG_HELLO = "Hello, %s! You should score %d points to pass the test. Good luck!";
    public static final String MSG_QUESTION = String.format("Choose the correct answer or type '%s' for exit", CMD_EXIT);
    public static final String ERR_EMPTY_STRING = "String value can not be blank!";
    public static final String ERR_ONE_CHAR_EXPECTED = "You should type one character!";
    public static final String ERR_OUT_OF_BOUNDS = "Character is out of bounds!";
    public static final String ERR_UNKNOWN = "Unknown error";
    public static final String MSG_SCORE = "Test complete!\n%s, your score is: %d";
    public static final String MSG_SUCCESS = "Congratulations, you successfully pass the test!";
    public static final String MSG_FAIL = "Sadly, you couldn't pass the test :(";
    public static final String MSG_TRY_AGAIN = String.format("Would you like to take the test one more time? Type '%s' to try again", CMD_YES);
    public static final String MSG_GOOD_BY = "Good by!";
    public static final String PROMPT = ">";

    private final int scoreToPass;
    private final TestingService testingService;
    private final IOService ioService;
    private final QuestionConverter questionConverter;
    private User user;


    private static String getIOErrorMessage(int errorCode) {
        switch (errorCode) {
            case IOService.ERR_BLANK_STRING:
                return ERR_EMPTY_STRING;
            case IOService.ERR_CHAR_EXPECTED:
                return ERR_ONE_CHAR_EXPECTED;
            default:
                return ERR_UNKNOWN;
        }
    }

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
        sayHello();
        if (registerUser()) {
            run();
        }
        sayGoodBy();
    }

    private void run() {
        do {
            if (!runTest()) {
                return;
            }
            if (!wantTryAgain()) {
                return;
            }
        } while (true);
    }


    private boolean processUserAnswer(Question question) {
        var result = true;
        ioService.println(questionConverter.convertQuestionToString(question));
        ioService.println(MSG_QUESTION);
        do {
            try {
                var ch = ioService.readChar(PROMPT, AppRunner::getIOErrorMessage);
                if (ch == CMD_EXIT) {
                    result = false;
                    break;
                }
                var answerIndex = ch - 'a';
                testingService.answerQuestion(answerIndex);
                break;
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
        var question = testingService.getNextQuestion();
        while (question != null) {
            if (!processUserAnswer(question)) {
                return false;
            }
            question = testingService.getNextQuestion();
        }
        return true;
    }


    private boolean runTest() {
        testingService.startTest();
        if (!answerQuestions()) {
            return false;
        }
        printScore();
        return true;
    }

    private void printScore() {
        var score = testingService.getScore();
        ioService.printlnFormat(MSG_SCORE, user.getFullName(), score);
        if (score >= scoreToPass) {
            ioService.println(MSG_SUCCESS);
        } else {
            ioService.println(MSG_FAIL);
        }
    }

    private boolean wantTryAgain() {
        ioService.println(MSG_TRY_AGAIN);
        var s = ioService.readString(PROMPT);
        return s.equalsIgnoreCase(Character.toString(CMD_YES));
    }

    private boolean registerUser() {

        var name =  ioService.readNotEmptyString(PROMPT, MSG_ENTER_NAME, AppRunner::getIOErrorMessage);
        if (name.equalsIgnoreCase(Character.toString(CMD_EXIT))) {
            return false;
        }

        var surname = ioService.readNotEmptyString(PROMPT, MSG_ENTER_SURNAME, AppRunner::getIOErrorMessage);
        if (name.equalsIgnoreCase(Character.toString(CMD_EXIT))) {
            return false;
        }

        user = new User(name, surname);
        ioService.printlnFormat(MSG_HELLO, user.getFullName(), scoreToPass);
        testingService.startTest();
        return true;
    }

    private void sayHello() {
        ioService.println(MSG_WELCOME);
    }
}
