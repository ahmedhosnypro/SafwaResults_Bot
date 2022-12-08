package com.safwah;

import com.formdev.flatlaf.FlatDarkLaf;
import com.power.PowerManagement;
import com.safwah.bot.Bot;
import com.safwah.bot.code.CodeBotRunner;
import com.safwah.bot.result.BotRunner;
import com.safwah.database.result.IdYearDataBase;
import com.safwah.gui.MainFrame;
import com.safwah.logger.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// use this to debug jar
//java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 SafwaResults_Bot.jar
public class Main {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private static String resourcePath = "C:\\SafwahResult_bot\\resources\\";

    public static final List<Bot> bots = new ArrayList<>();
    private static MainFrame mainFrame;

    public static void main(String[] args) {
        setLaf();
        mainFrame = new MainFrame();

//        ResultsDataBase.writeUsers();

        PowerManagement.INSTANCE.preventSleep();

        EXECUTOR.submit(() -> {
            while (true) {
                if (!bots.isEmpty() && !isInternetOn()) {
                    Logger.log("disconnected");
                    stopBots();
                    while (!isInternetOn()) {
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    runBots();
                }
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.log("connected");
            }
        });
    }

    private static void runBots() {
        bots.stream().forEach(b -> {
            if (b.getType().equals("resultBot")) {
                BotRunner.runBot(b);
            } else if (b.getType().equals("codeBot")) {
                CodeBotRunner.runBot(b);
            }
        });
    }

    private static void stopBots() {
        bots.stream().forEach(b -> {
            if (b.getType().equals("resultBot")) {
                BotRunner.stopBot(b, false);
            } else if (b.getType().equals("codeBot")) {
                CodeBotRunner.stopBot(b, false);
            }
        });
    }

    static void setLaf() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }


    public static void setResourcePath(String resourcePath) {
        Main.resourcePath = resourcePath;
        Logger.log(resourcePath);
        IdYearDataBase.updateSrcLocation();
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static String getResourcePath() {
        return resourcePath;
    }

    public static boolean isInternetOn() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec(new String[]{"ping", "-c", "1", "google.com"});
            int returnVal = p1.waitFor();
            return (returnVal == 0);
        } catch (Exception e) {
            return false;
        }
    }
}