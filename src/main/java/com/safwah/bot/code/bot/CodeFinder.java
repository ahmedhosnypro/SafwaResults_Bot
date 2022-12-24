package com.safwah.bot.code.bot;

import com.safwah.database.code.CodeDataBase;
import com.safwah.Student;
import com.safwah.logger.Logger;
import com.safwah.study.year.StudyYear1444;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeFinder {

    private CodeFinder() {
    }

    static final String EMAIL_REGEX = "(?:[a-z\\d!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z\\d!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z\\d](?:[a-z\\d-]*[a-z\\d])?\\.)+[a-z\\d](?:[a-z\\d-]*[a-z\\d])?|\\[(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?|[a-z\\d-]*[a-z\\d]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static void getCode(CodeBot bot, String messageText, long chatId, String username) {
        if (checkNameOrEmail(bot, messageText, chatId, username)) {
            String[] result = tryToGetCode(messageText, true);
            if (result != null) {
                bot.sendMessage(String.format("""
                        الاسم : <code> %s </code>
                                                
                        الكود :  👈👈  <code> %s </code>  👉👉
                                                
                        """, result[0], result[1]) +
                        "⚠️⚠️ برجاء استخدام هذا الكود في نموذج الاختبار يمكنك الضغط عليه وسيتم نسخه الى الحافظة ⚠️⚠️", chatId);
                Logger.log(messageText, username, "CODE", "normal_code");
            } else {
                bot.sendMessage(CodeMessageText.NOT_FOUND_NAME_ERROR.toString(), chatId);
                Logger.log(messageText, username, CodeMessageText.NOT_FOUND_NAME_ERROR.name(), "react_code");
            }

        }
    }

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

    public static String[] getCode(String messageText, StudyYear1444 year) {
        if (checkNameOrEmail(messageText)) {
            return CodeDataBase.getCode(messageText, year);
        }
        return null;
    }

    public static String[] getCodeByTryingMatchingNames(String fullName, StudyYear1444 year, boolean isRepeating) {
        return CodeDataBase.getCodeByTryingMatchingNames(fullName, year, isRepeating);
    }

    public static String[] getHigherCode(String messageText, StudyYear1444 year) {
        if (checkNameOrEmail(messageText)) {
            return CodeDataBase.getHigherCode(messageText, year);
        }
        return null;
    }

    public static Student getStudent(String resultCode, StudyYear1444 year) {
        return CodeDataBase.getStudent(resultCode, year);
    }


    static boolean checkNameOrEmail(CodeBot bot, String messageText, long chatId, String username) {
        boolean isEmail = isEmail(messageText);
        String header = "";
        if (isEmail && isInValidEmail(messageText)) {
            bot.sendMessage(header + CodeMessageText.EMAIL_ERROR, chatId);
            Logger.log(messageText, username, CodeMessageText.EMAIL_ERROR.name(), "normal_code");
            return false;
        } else if (!isEmail) {
            return true;
            //            var arr = messageText.split(" ");
//            if (arr.length < 3) {
//                bot.sendMessage(header + CodeMessageText.SHORT_NAME_ERROR, chatId);
//                Logger.log(messageText, username, header + CodeMessageText.SHORT_NAME_ERROR.name(), "normal_code");
//                return false;
//            }
//
//            if (!CodeDataBase.isExists(messageText)) {
//                bot.sendMessage(header + CodeMessageText.NOT_FOUND_NAME_ERROR, chatId);
//                Logger.log(messageText, username, header + CodeMessageText.NOT_FOUND_NAME_ERROR.name(), "react_code");
//                return false;
//            }
        } else {
            return true;
//            if (!CodeDataBase.isExists(messageText)) {
//                bot.sendMessage(header + CodeMessageText.NOT_FOUND_EMAIL_ERROR, chatId);
//                Logger.log(messageText, username, header + CodeMessageText.NOT_FOUND_EMAIL_ERROR.name(), "normal_code");
//                return false;
//            }
        }
    }

    static boolean checkNameOrEmail(String messageText) {
        boolean isEmail = isEmail(messageText);
        if (isEmail && isInValidEmail(messageText)) {
            return false;
        } else if (!isEmail) {
//            if (!CodeDataBase.isCodeExistsFor(messageText)) {
//                return false;
//            }
        } else {
            if (!CodeDataBase.isCodeExistsFor(messageText)) {
                return false;
            }
        }
        return true;
    }

    static boolean isEmail(String message) {
        return message.contains("@");
    }

    private static boolean isInValidEmail(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email.toLowerCase());
        return !emailMatcher.matches();
    }


}
