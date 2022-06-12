package com.safwa.bot;

import com.safwa.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor extends MyTelegramBot {
    static final String PROBLEM_REGEX = "مشكلة|مشكل|مشكله|كيف|المساعدة|ساعدني|راسب";
    public static final Pattern PROBLEM_PATTERN = Pattern.compile(PROBLEM_REGEX);

    void processCommand(String messageText, long chatId) {
        if (messageText != null && !messageText.isBlank() && !messageText.isEmpty()) {
            Matcher problemMatcher = PROBLEM_PATTERN.matcher(messageText);
            if (messageText.equals("/start")) {
                start(messageText, chatId);
            } else if (messageText.startsWith("الفرقة")) {
                addNewId(messageText, chatId);
            } else if (problemMatcher.find()) {
                sendMessage(MessageText.PROBLEM.toString(), chatId);
                Logger.log(messageText, chatId, MessageText.PROBLEM.name());
            } else if (messageText.equals("موعد اختبارات الإعادة")) {
                sendMessage(MessageText.SECOND_EXAM_INFO.toString(), chatId);
                Logger.log(messageText, chatId, MessageText.SECOND_EXAM_INFO.name());
            } else {
                ResultFinder.getResults(this, messageText, chatId);
            }
        }
    }
}
