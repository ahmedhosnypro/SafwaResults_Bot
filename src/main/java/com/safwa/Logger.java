package com.safwa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String SUCCESS_LOG_PATH = "C:\\SafwaResults\\success.log";
    private static final String FAILURE_LOG_PATH = "C:\\SafwaResults\\fail.log";

    static void logSuccess(String message, long chatId) {
        File successLogFile = new File(SUCCESS_LOG_PATH);
        if (!successLogFile.exists()) {
            try {
                boolean isCreated = successLogFile.createNewFile();
                if (isCreated) {
                    System.out.println("created log file " + successLogFile.getAbsolutePath());
                }
            } catch (IOException ignored) {
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(SUCCESS_LOG_PATH), true), true, StandardCharsets.UTF_16)) {
            String printMessage = "\n--------------------" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) +
                    "--------------------\n" +
                    message + "\n--------------------------------------------------------\n\n";
            writer.println(printMessage);
            System.out.println("\n--" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) +
                    "-----" + chatId + "-----\n");
        } catch (IOException e) {
            System.out.println("can't find: " + SUCCESS_LOG_PATH);
        }
    }

    static void logFailure(String message) {
        File failureLogFile = new File(FAILURE_LOG_PATH);
        if (!failureLogFile.exists()) {
            try {
                boolean isCreated = failureLogFile.createNewFile();
                if (isCreated) {
                    System.out.println("created log file " + failureLogFile.getAbsolutePath());
                }
            } catch (IOException ignored) {
            }
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(FAILURE_LOG_PATH), true), true, StandardCharsets.UTF_16)) {
            writer.println(message);
        } catch (IOException e) {
            System.out.println("can't find: " + FAILURE_LOG_PATH);
        }
    }

}
