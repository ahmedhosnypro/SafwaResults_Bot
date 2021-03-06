package com.safwah.bot;

import com.safwah.database.IdYearDataBase;
import com.safwah.logger.Logger;
import com.safwah.study.year.StudyYear;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class MyTelegramBot extends TelegramLongPollingBot {

    void start(String messageText, long chatId, String userName) {
        inlineMarkupTitled(MessageText.START_MESSAGE.toString(), chatId);
        Logger.log(messageText, userName, MessageText.START_MESSAGE.name(), "info");
    }

    void inlineMarkupTitled(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            Logger.log(e.getMessage());
        }
    }

    void sendMessage(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            Logger.log(e.getMessage());
        }
    }

    void addNewId(String messageText, long chatId, String username) {
        StudyYear studyYear = IdYearDataBase.getStudyYear(chatId);

        String message = "اكتب البريد أو الاسم للبحث عن النتيجة";
        if (studyYear == StudyYear.ERROR) {
            message = MessageText.INPUT_NAME_OR_EMAIL_INSTRUCTIONS.toString();
        }
        if (IdYearDataBase.setIdYear(chatId, messageText)) {
            studyYear = IdYearDataBase.getStudyYear(chatId);
            message = "تم اختيار: " + studyYear.getArabicNotation() + "\n" + message;
            sendMessage(message, chatId);
            Logger.log(messageText, username, MessageText.INPUT_NAME_OR_EMAIL_INSTRUCTIONS.name(), "info");
        } else {
            inlineMarkupTitled("حاول مجددا", chatId);
            Logger.log(messageText, username, "SELECT_YEAR_ERROR", "info");
        }
    }

    void reply(Message replyMessage, String outputMessageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyToMessageId(replyMessage.getMessageId());
        sendMessage.setText(outputMessageText);
        sendMessage.setChatId(String.valueOf(replyMessage.getChatId()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            Logger.log(e.getMessage());
        }
    }
}