package com.safwah.bot;

import com.safwah.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TelegramBotExecutor extends MyTelegramBot {
    static final String PROBLEM_REGEX = "مشكلة|مشكل|مشكله|كيف|المساعدة|ساعدني|راسب|لماذا|[?]|؟";
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

    void callBack(String callData,  long chatId) {
        if (callData.startsWith("الفرقة")) {
            addNewId(callData, chatId);
        }
    }
}
