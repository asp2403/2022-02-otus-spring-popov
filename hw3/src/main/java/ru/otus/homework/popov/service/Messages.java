package ru.otus.homework.popov.service;

public class Messages {
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

    public static String getIOErrorMessage(int errorCode) {
        switch (errorCode) {
            case IOService.ERR_BLANK_STRING:
                return Messages.ERR_EMPTY_STRING;
            case IOService.ERR_CHAR_EXPECTED:
                return Messages.ERR_ONE_CHAR_EXPECTED;
            default:
                return Messages.ERR_UNKNOWN;
        }
    }

}
