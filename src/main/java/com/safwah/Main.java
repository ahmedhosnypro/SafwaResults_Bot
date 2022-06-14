package com.safwah;

import com.formdev.flatlaf.FlatDarkLaf;
import com.safwah.database.IdYearDataBase;
import com.safwah.gui.MainFrame;
import com.safwah.bot.Bot;
import com.safwah.logger.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// use this to debug jar
//java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 bot2.jar
public class Main {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private static String RESOURCE_PATH = "D:\\SafwahResult_bot\\resources\\";


    private static MainFrame mainFrame;

    public static void main(String[] args) {
        setLaf();
        mainFrame = new MainFrame();
    }


    static void setLaf() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    public static void startMainBot() {
        try {
            Bot mainBot = mainFrame.getMainBot();
            Logger.log("", 0, "--Connecting to Bot: " + mainBot.getBotName() + "----");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            SafwaResultsBot tgBot = new SafwaResultsBot();
//            telegramBotsApi.registerBot(tgBot);
            Logger.log("", 0, "--Bot : " + mainBot.getBotName() + " Connected Successfully ----");
        } catch (TelegramApiException e) {
            Logger.log("", 0, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void startTestBot() {
        try {
            Bot testBot = mainFrame.getTestBot();
            Logger.log("", 0, "--Connecting to Bot: " + testBot.getBotName() + "----");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            SafwaResultsBot tgBot = new SafwaResultsBot();
//            telegramBotsApi.registerBot(tgBot);
            Logger.log("", 0, "--Bot : " + testBot.getBotName() + " Connected Successfully ----");
        } catch (TelegramApiException e) {
            Logger.log("", 0, Arrays.toString(e.getStackTrace()));
        }
    }

    public static void setResourcePath(String resourcePath) {
        RESOURCE_PATH = resourcePath;
        Logger.log("", 0, resourcePath);
        Logger.setLogLocation();
        IdYearDataBase.updateSrcLocation();
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static String getResourcePath() {
        return RESOURCE_PATH;
    }
}