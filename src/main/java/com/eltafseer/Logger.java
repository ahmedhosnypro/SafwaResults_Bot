package com.eltafseer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final String SUCCESS_LOG_PATH = "D:\\13-Projects\\bot2\\src\\main\\resources\\success.log";
    private static final String FAILURE_LOG_PATH = "D:\\13-Projects\\bot2\\src\\main\\resources\\fail.log";

    static void logSuccess(String message) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(SUCCESS_LOG_PATH), true), true, StandardCharsets.UTF_16)) {
            String printMessage = "\n--------------------" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) +
                    "--------------------\n" +
                    message + "\n--------------------------------------------------------\n\n";
            writer.println(printMessage);
            System.out.println(printMessage);
        } catch (IOException e) {
            System.out.println("can't find: " + SUCCESS_LOG_PATH);
        }
    }

    static void logFailure(String message) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(FAILURE_LOG_PATH), true), true, StandardCharsets.UTF_16)) {
            writer.println(message);
        } catch (IOException e) {
            System.out.println("can't find: " + FAILURE_LOG_PATH);
            System.out.println(message);
        }
    }

}
