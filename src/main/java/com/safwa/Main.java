package com.safwa;

import com.safwa.bot.SafwaResultsBot;
import com.safwa.logger.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 bot2.jar
public class Main {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    public static void main(String[] args) {
//         Instantiate Telegram Bots API
        Logger.log("", 0, "--------------started-----------------\n\n");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            SafwaResultsBot bot = new SafwaResultsBot();
            telegramBotsApi.registerBot(bot);
            Logger.log("", 0, "------------Connected to Telegram-------------");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}