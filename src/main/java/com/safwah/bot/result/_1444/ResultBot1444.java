package com.safwah.bot.result._1444;

import com.safwah.logger.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class ResultBot1444 extends TelegramLongPollingBot {

    void start(String messageText, long chatId, String userName) {
        inlineMarkupTitled(ResultMessageText1444.START_MESSAGE.toString(), chatId);
        Logger.log(messageText, userName, ResultMessageText1444.START_MESSAGE.name(), "info");
    }

    void inlineMarkupTitled(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(ResultMenu1444.Menu());
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
        message.setReplyMarkup(ResultMenu1444.Menu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            Logger.log(e.getMessage());
        }
    }
}