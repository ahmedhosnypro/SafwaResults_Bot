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

    private static final String LOG_NAME = "SafwahLog.log";
    private static String logPath = "D:\\SafwahResult_bot\\resources\\" + LOG_NAME;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static void log(String messageText, long chatId, String outputMessage) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(logPath, true), true, StandardCharsets.UTF_16)) {
            String printMessage = "\n-" +
                    new SimpleDateFormat(DATE_FORMAT).format(new Date()) +
                    "- input: " + messageText + "- chatId: " + chatId + ":" +
                    outputMessage + "\n\n";
            writer.println(printMessage);
            Main.getMainFrame().updateLog(printMessage);
        } catch (IOException e) {
            Main.getMainFrame().updateLog("can't find: " + logPath);
        }
    }

    public static void setLogLocation() {
        logPath = Main.getResourcePath() + LOG_NAME;
        Logger.log("", 0,Main.getResourcePath() + LOG_NAME);
    }
}
