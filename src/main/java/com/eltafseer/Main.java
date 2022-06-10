package com.eltafseer;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        // Instantiate Telegram Bots API
        Logger.logSuccess("--------------started-----------------\n\n");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            SafwaResultsBot bot = new SafwaResultsBot();
            telegramBotsApi.registerBot(bot);
            Logger.logSuccess("------------Connected to Telegram-------------");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}