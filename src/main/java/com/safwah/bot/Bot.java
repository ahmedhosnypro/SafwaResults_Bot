package com.safwah.bot;

import javax.swing.*;

public class Bot {
    private final String botName;
    private final String botToken;

    private final JTextField botNameField;
    private final JTextField botTokenField;

    private final JButton botStartButton;
    private final JButton botStopButton;

    private  final  String type;

    public Bot(JTextField botNameField, JTextField botTokenField, JButton botStartButton, JButton botStopButton, String type) {
        this.botName = botNameField.getText();
        this.botToken = botTokenField.getText();
        this.botNameField = botNameField;
        this.botTokenField = botTokenField;
        this.botStartButton = botStartButton;
        this.botStopButton = botStopButton;
        this.type = type;
    }

    public void setBotFieldStatus(boolean status) {
        if (status) {
            botNameField.setEditable(false);
            botTokenField.setEditable(false);
            botStartButton.setEnabled(false);
            botStopButton.setEnabled(true);
        } else {
            botNameField.setEditable(true);
            botTokenField.setEditable(true);
            botStartButton.setEnabled(true);
            botStopButton.setEnabled(false);
        }
    }

    public String getBotName() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getType() {
        return type;
    }
}
