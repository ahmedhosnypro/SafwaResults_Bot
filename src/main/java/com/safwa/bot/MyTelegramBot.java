package com.safwa.bot;

import com.safwa.database.IdYearDataBase;
import com.safwa.logger.Logger;
import com.safwa.study.year.StudyYear;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class MyTelegramBot extends SafwaResultsBot {

    void start(String messageText, long chatId) {
        inlineMarkupTitled(MessageText.START_MESSAGE.toString(), chatId);
        Logger.log(messageText, chatId, MessageText.START_MESSAGE.name());
    }

    void inlineMarkupTitled(String messageText, long chatId) {
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

    void sendMessage(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setParseMode(ParseMode.HTML);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void addNewId(String messageText, long chatId) {
        StudyYear studyYear = IdYearDataBase.getStudyYear(chatId);

        String message = "اكتب البريد أو الاسم للبحث عن النتيجة";
        boolean fstTimeInstruct = false;
        if (studyYear == StudyYear.ERROR) {
            fstTimeInstruct = true;
            message = MessageText.INPUT_NAME_OR_EMAIL_INSTRUCTIONS.toString();
        }
        if (IdYearDataBase.setIdYear(chatId, messageText)) {
            studyYear = IdYearDataBase.getStudyYear(chatId);
            if (fstTimeInstruct) {
                message = "تم اختيار: " + studyYear.getArabicNotation() + "\n" + message;
            } else {
                message = "تم اختيار: " + studyYear.getArabicNotation() + "\n" + message;
            }
            sendMessage(message, chatId);
            Logger.log(messageText, chatId, MessageText.INPUT_NAME_OR_EMAIL_INSTRUCTIONS.name());
        } else {
            inlineMarkupTitled("حاول مجددا", chatId);
            Logger.log(messageText, chatId, "SELECT_YEAR_ERROR");
        }
    }
}
