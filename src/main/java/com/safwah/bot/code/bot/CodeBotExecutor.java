package com.safwah.bot.code.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public abstract class CodeBotExecutor extends CodeBot {
    public void processCommand(Message message) {
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
            if (messageText.equals("/start")) {
                start(messageText, chatId, userName);
            } else if (!messageText.equals("أدخل اسمك أو بريدك الإلكتروني")) {
                CodeFinder.getCode(this, messageText, chatId, userName);
            }
        }
    }

    public void callBack(String callData, long chatId, String userName) {
    }
}
