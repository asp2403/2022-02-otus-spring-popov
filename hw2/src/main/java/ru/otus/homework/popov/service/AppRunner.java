package ru.otus.homework.popov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.popov.domain.Question;
import ru.otus.homework.popov.domain.User;

import java.util.List;
@Service
public class AppRunner {

    public static final String CMD_EXIT = "q";
    public static final String MSG_WELCOME = String.format("Welcome to the test! Enter your name, please. Type '%s' for exit", CMD_EXIT);
    public static final String MSG_HELLO = "Hello, %s! You should score %d points to pass the test. Good luck!";
    public static final String MSG_QUESTION = String.format("Choose the correct answer or type '%s' for exit", CMD_EXIT);
    public static final String ERR_EMPTY_STRING = "String value can not be blank!";
    public static final String ERR_STRING_TOO_LONG = "You should type one character!";
    public static final String ERR_OUT_OF_BOUNDS = "Character is out of bounds!";
    public static final String MSG_SCORE = "Test complete. Your score is: %d";
    public static final String MSG_SUCCESS = "Congratulations, you successfully pass the test!";
    public static final String MSG_FAIL = "Sadly you couldn't pass the test. Type 'y' to try again or any symbol for exit";
    public static final String MSG_GOOD_BY = "Good by!";
    public static final String PROMPT = ">";

    private final int scoreToPass;
    private final QuestionService questionService;
    private final IOService ioService;
    private final QuestionConverter questionConverter;
    private User user;
    private List<Question> questions;

    public AppRunner(
            @Value("${scoreToPass}") int scoreToPass,
            IOService ioService,
            QuestionConverter questionConverter,
            QuestionService questionService) {
        this.scoreToPass = scoreToPass;
        this.ioService = ioService;
        this.questionService = questionService;
        this.questionConverter = questionConverter;
    }

    public void execute() {
        if (!welcomeUser()) {
            sayGoodBy();
            return;
        }
        loadQuestions();
        run();
    }

    private boolean processUserAnswer(int questionIndex) {
        var result = true;
        ioService.println(questionConverter.convertQuestionToString(questionIndex, questions.get(questionIndex)));
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
                var question = questions.get(questionIndex);
                var answerIndex = s.charAt(0) - 'a';
                try {
                    var answer = question.getAnswers().get(answerIndex);
                    if (answer.isCorrect()) {
                        user.addScore();
                    }
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
        for (var index = 0; index < questions.size(); index++) {
            if (!processUserAnswer(index)) {
                sayGoodBy();
                return;
            }
        }
        ioService.println(String.format(MSG_SCORE, user.getScore()));
        if (user.getScore() >= scoreToPass) {
            ioService.println(MSG_SUCCESS);
            sayGoodBy();
        } else {
            ioService.println(MSG_FAIL);
            var s = ioService.readString(PROMPT);
            if (s.toLowerCase().equals("y")) {
                user.resetScore();
                run();
            } else {
                sayGoodBy();
            }
        }

    }

    private void loadQuestions() {
        questions = questionService.getQuestions();
    }

    private boolean welcomeUser() {
        ioService.println(MSG_WELCOME);
        do {
            var userName = ioService.readString(PROMPT);
            if (userName.toLowerCase().equals(CMD_EXIT)) {
                return false;
            }
            if (userName.isEmpty()) {
                ioService.println(ERR_EMPTY_STRING);
            } else {
                user = new User(userName);
                ioService.println(String.format(MSG_HELLO, user.getName(), scoreToPass));
                return true;
            }
        } while (true);
    }
}
