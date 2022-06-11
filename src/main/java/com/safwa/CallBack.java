package com.safwa;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CallBack extends SafwaResultsBot {
    void callBack(String callData,  long chatId) {
        if (callData.startsWith("الفرقة")) {
            addNewId(callData, chatId);
        }
    }

    void addNewId(String messageText, long chatId) {
        if (IdYearDataBase.setIdYear(chatId, messageText)) {
            sendMessage("اكتب اسمك رباعيا أو بريدك الإلكتروني", chatId);
            Logger.logSuccess("اكتب اسمك رباعيا أو بريدك الإلكتروني");
        } else {
            sendYearsWithFeedBack("حاول مجددا", chatId);
            Logger.logSuccess("حاول مجددا");
        }
    }



    void sendMessage(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendYearsWithFeedBack(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
