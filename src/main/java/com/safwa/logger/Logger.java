package com.safwa.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String SUCCESS_LOG_PATH = "C:\\SafwaResults\\success.log";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static void log(String messageText, long chatId, String outputMessage) {
        File successLogFile = new File(SUCCESS_LOG_PATH);
        if (!successLogFile.exists()) {
            try {
                boolean isCreated = successLogFile.createNewFile();
                if (isCreated) {
                    System.out.println("created log file " + successLogFile.getAbsolutePath());
                }
            } catch (IOException ignored) {
                //ignored
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(SUCCESS_LOG_PATH), true), true, StandardCharsets.UTF_16)) {
            String printMessage = "\n-" +
                    new SimpleDateFormat(DATE_FORMAT).format(new Date()) +
                    "--- input: " + messageText + "--- chatId: " + chatId + "-\n" +
                    outputMessage + "\n--------------------------------------------------------\n\n";
            writer.println(printMessage);
            System.out.println(printMessage);
        } catch (IOException e) {
            System.out.println("can't find: " + SUCCESS_LOG_PATH);
        }
    }
}
