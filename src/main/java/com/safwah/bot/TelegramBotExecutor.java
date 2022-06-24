package com.safwah.bot;

import com.safwah.logger.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TelegramBotExecutor extends MyTelegramBot {
    static final String PROBLEM_REGEX = "مشكلة|مشكل|مشكله|كيف|المساعدة|ساعدني|راسب|لماذا|متى|أين|هل|أريد|اريد|استطيع|أستطيع|[?]|؟";

    public static final Pattern PROBLEM_PATTERN = Pattern.compile(PROBLEM_REGEX);

    void processCommand(Message message) {
        String messageText = message.getText();
        long chatId = message.getChatId();
        User user = message.getFrom();
        String userName;
        if (user.getUserName() != null) {
            userName = user.getUserName();
        } else {
            if (user.getLastName() != null) {
                userName = user.getFirstName() + user.getLastName();
            } else {
                userName = user.getFirstName();
            }
        }
        if (messageText != null && !messageText.isBlank() && !messageText.isEmpty()) {
            Matcher problemMatcher = PROBLEM_PATTERN.matcher(messageText);
            if (messageText.equals("/start")) {
                start(messageText, chatId, userName);
            } else if (messageText.startsWith("الفرقة")) {
                addNewId(messageText, chatId, userName);
            } else if (problemMatcher.find()) {
                reply(message, MessageText.PROBLEM.toString());
                Logger.log(messageText, userName, MessageText.PROBLEM.name(), "react");
            } else if (messageText.equals("موعد اختبارات الإعادة")) {
                sendMessage(MessageText.SECOND_EXAM_INFO.toString(), chatId);
                Logger.log(messageText, userName, MessageText.SECOND_EXAM_INFO.name(), "info");
            } else {
                ResultFinder.getResults(this, messageText, chatId, userName);
            }
        }
    }

    void callBack(String callData, long chatId, String userName) {
        if (callData.startsWith("الفرقة")) {
            addNewId(callData, chatId, userName);
        }
    }
}
