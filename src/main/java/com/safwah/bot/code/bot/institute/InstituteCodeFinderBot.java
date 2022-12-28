package com.safwah.bot.code.bot.institute;

import com.safwah.Student;
import com.safwah.bot.code.bot.CodeBot;
import com.safwah.database.code.CodeDataBase;
import com.safwah.database.code.institute.InstituteCodeDataBase;
import com.safwah.study.year.institute.InstituteStudyYear1444;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstituteCodeFinderBot {

    private InstituteCodeFinderBot() {
    }

    static final String EMAIL_REGEX = "(?:[a-z\\d!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z\\d!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z\\d](?:[a-z\\d-]*[a-z\\d])?\\.)+[a-z\\d](?:[a-z\\d-]*[a-z\\d])?|\\[(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?|[a-z\\d-]*[a-z\\d]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Nullable
    public static String[] tryToGetCode(String messageText, Boolean isRepeating) {
        String[] result = CodeDataBase.getCode(messageText);
        if (result == null) {
            result = CodeDataBase.getCodeByTryingMatchingNames(messageText, isRepeating);
        }
        return result;
    }

    public static String[] getCode(String messageText) {
        if (checkNameOrEmail(messageText)) {
            return CodeDataBase.getCode(messageText);
        }
        return null;
    }

    public static String[] getCode(String messageText, InstituteStudyYear1444 year) {
        if (checkNameOrEmail(messageText)) {
            return InstituteCodeDataBase.getCode(messageText, year);
        }
        return null;
    }

    public static String[] getCodeByTryingMatchingNames(String fullName, InstituteStudyYear1444 year, boolean isRepeating) {
        return InstituteCodeDataBase.getCodeByTryingMatchingNames(fullName, year, isRepeating);
    }

    public static String[] getHigherCode(String messageText, InstituteStudyYear1444 year) {
        if (checkNameOrEmail(messageText)) {
            return InstituteCodeDataBase.getHigherCode(messageText, year);
        }
        return null;
    }

    public static Student getStudent(String resultCode, InstituteStudyYear1444 year) {
        return InstituteCodeDataBase.getStudent(resultCode, year);
    }


    static boolean checkNameOrEmail(CodeBot bot, String messageText, long chatId, String username) {
        boolean isEmail = isEmail(messageText);
        String header = "";
        if (isEmail && isInValidEmail(messageText)) {
            return false;
        }
        return true;
    }

    static boolean checkNameOrEmail(String messageText) {
        boolean isEmail = isEmail(messageText);
        return !isEmail || !isInValidEmail(messageText);

    }

    static boolean isEmail(String message) {
        return message.contains("@");
    }

    private static boolean isInValidEmail(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email.toLowerCase());
        return !emailMatcher.matches();
    }
}
