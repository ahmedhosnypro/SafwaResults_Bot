package com.safwah.bot;

import com.safwah.Main;
import com.safwah.logger.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

public class BotRunner {
    private static final Map<TelegramBotExecutor, BotSession> sessions = new HashMap<>();

    public static void runBot(Bot bot) {
        TelegramBotExecutor tgBot = new TelegramBotExecutor() {
            @Override
            public void onUpdateReceived(Update update) {
                onUpdate(this, update);
            }

            @Override
            public String getBotUsername() {
                return bot.getBotName();
            }

            @Override
            public String getBotToken() {
                return bot.getBotToken();
            }
        };
        // Instantiate Telegram Bots API
        Logger.log("", 0, "--Connecting to Bot: " + bot.getBotName() + "----");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            var session = telegramBotsApi.registerBot(tgBot);
            if (session != null && session.isRunning()) {
                Logger.log("", 0, "--Bot : " + bot.getBotName() + " Connected Successfully ----");
                sessions.put(tgBot, session);
                Main.EXECUTOR.submit(session::start);
            }
        } catch (TelegramApiException e) {
            Logger.log("", 0, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void stopBot(Bot bot) {
        Logger.log("", 0, "--Disconnecting: " + bot.getBotName() + " Bot----");

        Main.EXECUTOR.submit(() -> {
            var opt = sessions.keySet().stream()
                    .filter(b -> b.getBotUsername().equals(bot.getBotName()))
                    .map(sessions::get).findFirst();
            if (opt.isPresent()) {
                var setOfThread = Thread.getAllStackTraces().keySet();
                for (Thread thread : setOfThread) {
                    String name = thread.getName();
                    if (name.startsWith(bot.getBotName())) {
                        try {
                            thread.join(200L);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                        thread.interrupt();
                        System.out.println(thread.isAlive());
                    }
                }
                opt.get().stop();
                Logger.log("", 0, "--Bot: " + bot.getBotName() + "Disconnected Successfully ----");
            } else {
                Logger.log("", 0, "-- can't stop " + bot.getBotName() + " Bot----");
            }
        });
    }

    static void onUpdate(TelegramBotExecutor executor, Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Main.EXECUTOR.submit(() -> executor.processCommand(update.getMessage().getText(), update.getMessage().getChatId()));
        } else if (update.hasCallbackQuery()) {
            Main.EXECUTOR.submit(() -> {
                executor.callBack(update.getCallbackQuery().getData(),
                        update.getCallbackQuery().getMessage().getChatId());
            });
        }
    }
}
