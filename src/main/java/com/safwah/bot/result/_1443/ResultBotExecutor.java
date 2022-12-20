package com.safwah.bot.result._1443;

import com.safwah.logger.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
public abstract class ResultBotExecutor extends ResultBot{
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
            } else if (messageText.startsWith("الفرقة")) {
                addNewId(messageText, chatId, userName);
            } else if (messageText.equals("موعد اختبارات الإعادة")) {
                sendMessage(ResultMessageText.SECOND_EXAM_INFO.toString(), chatId);
                Logger.log(messageText, userName, ResultMessageText.SECOND_EXAM_INFO.name(), "info");
            } else {
                ResultFinder.getResults(this, messageText, chatId, userName);
            }
        }
    }

    public void callBack(String callData, long chatId, String userName) {
        if (callData.startsWith("الفرقة")) {
            addNewId(callData, chatId, userName);
        }
    }
}
