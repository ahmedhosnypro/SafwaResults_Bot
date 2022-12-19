package com.safwah.bot.result._1444;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public abstract class ResultBotExecutor1444 extends ResultBot1444 {
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
            } else if (messageText.equals("/help") || messageText.equals("الحصول على مساعدة")) {
                sendMessage(ResultMessageText1444.HELP.toString(), chatId);
            } else {
                ResultFinder1444.getResults(this, messageText, chatId, userName); // messageText = code
            }
        }
    }

    public void callBack(String callData, long chatId, String userName) {
    }
}
