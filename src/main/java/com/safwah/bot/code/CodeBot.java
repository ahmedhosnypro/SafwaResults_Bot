package com.safwah.bot.code;

import com.safwah.logger.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class CodeBot extends TelegramLongPollingBot {

    void start(String messageText, long chatId, String userName) {
        inlineMarkupTitled(CodeMessageText.START_MESSAGE.toString(), chatId);
        Logger.log(messageText, userName, CodeMessageText.START_MESSAGE.name(), "info_code");
    }

    void inlineMarkupTitled(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(CodeMenu.codeMenu());
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
        message.setReplyMarkup(CodeMenu.codeMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            Logger.log(e.getMessage());
        }
    }
}