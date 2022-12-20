package com.safwah.bot.code;

import com.safwah.Main;
import com.safwah.bot.Bot;
import com.safwah.logger.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CodeBotRunner {
    private static final Map<CodeBotExecutor, BotSession> sessions = new HashMap<>();

    public static void runBot(Bot bot) {
        CodeBotExecutor resultBot = new CodeBotExecutor() {
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
        Logger.log("--Connecting to Bot: " + bot.getBotName() + "----");
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            var session = telegramBotsApi.registerBot(resultBot);
            if (session != null && session.isRunning()) {
                bot.setBotFieldStatus(true);
                Logger.log(bot.getBotName() + " Connected Successfully ----");
                sessions.put(resultBot, session);
                Main.EXECUTOR.submit(session::start);
                Main.bots.add(bot);
            }
        } catch (TelegramApiException e) {
            bot.setBotFieldStatus(false);
            Logger.log("", "", "Error Connecting to Bot: " + bot.getBotName() + " ----", "error");
        }
    }

    public static void stopBot(Bot bot, boolean remove) {
        Logger.log("--Disconnecting: " + bot.getBotName() + " Bot----");

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

                if (remove) {
                    Main.bots.remove(bot);
                    bot.setBotFieldStatus(false);
                }

                Logger.log("--Bot: " + bot.getBotName() + "Disconnected Successfully ----");

            } else {
                Logger.log("", "", "-- can't stop " + bot.getBotName() + " Bot----", "error");
            }
        });
    }

    static void onUpdate(CodeBotExecutor executor, Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Main.EXECUTOR.submit(() -> {
                try {
                    executor.processCommand(update.getMessage());
                } catch (Exception e) {
                    Logger.log(Arrays.toString(e.getStackTrace()));
                }
            });
        } else if (update.hasCallbackQuery()) {
            Main.EXECUTOR.submit(() -> executor.callBack(update.getCallbackQuery().getData(),
                    update.getCallbackQuery().getMessage().getChatId(), update.getChatJoinRequest().getUser().getUserName()));
        }
    }
}
