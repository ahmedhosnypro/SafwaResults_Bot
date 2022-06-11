package com.safwa;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SafwaResultsBot extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            Main.executor.submit(() -> {
                CommandProcessor commander = new CommandProcessor();
                commander.processCommand(messageText, chatId);
            });
        } else if (update.hasCallbackQuery()) {
            Main.executor.submit(() -> {
                CallBack callBack = new CallBack();
                callBack.callBack(update.getCallbackQuery().getData(),
                        update.getCallbackQuery().getMessage().getChatId());
            });

        }
    }

    @Override
    public String getBotUsername() {
        return "SafwaResults_bot";
    }

    @Override
    public String getBotToken() {
        return "5510651333:AAFnGs9PpkEX-IAaY9gleR-2P-AVonqtogs";
    }
}