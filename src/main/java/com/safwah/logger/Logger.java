package com.safwah.logger;

import com.safwah.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private Logger() {
    }

    private static final String LOG_PATH = "C:\\SafwahResult_bot\\log\\";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static void log(String messageText, String username, String outputMessage, String type) {
        String path = LOG_PATH + type + ".log";
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(path, true), true, StandardCharsets.UTF_16)) {
            String printMessage = "\n-" +
                    new SimpleDateFormat(DATE_FORMAT).format(new Date()) +
                    " - from: " + username + "\n input: " + messageText + "\nreply: " +
                    outputMessage + "\n\n";
            writer.println(printMessage);
            Main.getMainFrame().updateLog(printMessage, type);
        } catch (IOException e) {
            Main.getMainFrame().updateLog("can't find: " + LOG_PATH + type + ".log", "error");
        }
    }

    public static void log(String log) {
        String path = LOG_PATH + "info" + ".log";
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(path, true), true, StandardCharsets.UTF_16)) {
            String printMessage = "\n-" +
                    new SimpleDateFormat(DATE_FORMAT).format(new Date()) +
                    " " + log + "\n\n";
            writer.println(printMessage);
            Main.getMainFrame().updateLog(printMessage, "info");
        } catch (IOException e) {
            Main.getMainFrame().updateLog("can't find: " + path, "error");
        }
    }
}
