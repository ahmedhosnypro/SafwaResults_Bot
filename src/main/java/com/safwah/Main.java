package com.safwah;

import com.formdev.flatlaf.FlatDarkLaf;
import com.safwah.database.result.IdYearDataBase;
import com.safwah.gui.MainFrame;
import com.safwah.logger.Logger;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// use this to debug jar
//java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 SafwaResults_Bot.jar
public class Main {

    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    private static String resourcePath = "D:\\SafwahResult_bot\\resources\\";


    private static MainFrame mainFrame;

    public static void main(String[] args) {
        setLaf();
        mainFrame = new MainFrame();
//        ResultsDataBase.writeUsers();
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
}