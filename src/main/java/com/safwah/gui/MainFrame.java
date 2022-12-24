/*
 * Created by JFormDesigner on Mon Jun 13 16:13:15 EET 2022
 */

package com.safwah.gui;

import javax.swing.event.*;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.safwah.Main;
import com.safwah.bot.Bot;
import com.safwah.bot.code.bot.CodeBotRunner;
import com.safwah.bot.result._1443.BotRunner;
import com.safwah.bot.result._1444.BotRunner1444;
import li.flor.nativejfilechooser.NativeJFileChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.lang.Thread.sleep;

/**
 * @author unknown
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 300);
        initComponents();

        pack();
        setVisible(true);
    }

    private void selectResDir(ActionEvent e) {
        JFileChooser fileChooser = new NativeJFileChooser("D:\\SafwahResult_bot\\resources");
        fileChooser.setFileFilter(new FileNameExtensionFilter("images", "*.jpg"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showSaveDialog(null);

        Main.setResourcePath(fileChooser.getSelectedFile() + "\\");
    }


    /*main bot*/
    private void stopMainBot(ActionEvent e) {
        BotRunner.stopBot(new Bot(mainBotNameTextField, mainBotTokenTextField, startMainBotButton, stopMainBotButton, "resultBot"), true);
    }

    private void startMainBot(ActionEvent e) {
        BotRunner.runBot(new Bot(mainBotNameTextField, mainBotTokenTextField, startMainBotButton, stopMainBotButton, "resultBot"));
    }

    /*test bot*/
    private void stopTestBot(ActionEvent ignored) {
        BotRunner.stopBot(new Bot(testBotNameTextField, testBotTokenTextField, startTestBotButton, stopTestBotButton, "resultBot"), true);
    }

    private void startTestBot(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            BotRunner.runBot(new Bot(testBotNameTextField, testBotTokenTextField, startTestBotButton, stopTestBotButton, "resultBot"));
        });
    }

    private void reply(ActionEvent e) {
        // TODO add your code here
    }

    /*code bot*/
    private void startCodeBot(ActionEvent e) {
        CodeBotRunner.runBot(new Bot(codeBotNameTextField, codeBotTokenTextField, startCodeBotButton, stopCodeBotButton, "codeBot"));
    }

    private void stopCodeBot(ActionEvent e) {
        CodeBotRunner.stopBot(new Bot(codeBotNameTextField, codeBotTokenTextField, startCodeBotButton, stopCodeBotButton, "codeBot"), true);
    }

    /*code test bot*/
    private void startCodeTestBot(ActionEvent e) {
        CodeBotRunner.runBot(new Bot(codeTestBotNameTextField, codeTestBotTokenTextField, startCodeTestBotButton, stopCodeTestBotButton, "codeBot"));
    }

    private void stopCodeTestBot(ActionEvent e) {
        CodeBotRunner.stopBot(new Bot(codeTestBotNameTextField, codeTestBotTokenTextField, startCodeTestBotButton, stopCodeTestBotButton, "codeBot"), true);
    }

    private void button2StateChanged(ChangeEvent e) {
        // TODO add your code here
    }

    private void button3(ActionEvent e) {
        // TODO add your code here
    }

    private void startMainBot1444(ActionEvent e) {
        BotRunner1444.runBot(new Bot(mainBot1444NameTextField, mainBot1444TokenTextField, startMainBot1444Button, stopMainBot1444Button, "resultBot1444"));
    }

    private void stopMainBot1444(ActionEvent e) {
        BotRunner1444.stopBot(new Bot(mainBot1444NameTextField, mainBot1444TokenTextField, startMainBot1444Button, stopMainBot1444Button, "resultBot1444"), true);
    }

    private void startTestBot1444(ActionEvent e) {
        BotRunner1444.runBot(new Bot(testBot1444NameTextField, testBot1444TokenTextField, startTestBot1444Button, stopTestBot1444Button, "resultBot1444"));
    }

    private void stopTestBot1444(ActionEvent e) {
        BotRunner1444.stopBot(new Bot(testBot1444NameTextField, testBot1444TokenTextField, startTestBot1444Button, stopTestBot1444Button, "resultBot1444"), true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - ahmed
        botsPanel = new JPanel();
        resDirPanel = new JPanel();
        selectResDirButton = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        resDirSeparator = new JSeparator();
        botSettingsPanel = new JPanel();
        mainBotPanel = new JPanel();
        mainBotNameLabel = new JLabel();
        mainBotNameTextField = new JTextField();
        mainBotTokenLabel = new JLabel();
        mainBotTokenTextField = new JTextField();
        startMainBotButton = new JButton();
        stopMainBotButton = new JButton();
        botSeparator = new JSeparator();
        mainBot1444Panel = new JPanel();
        mainBot1444NameLabel = new JLabel();
        mainBot1444NameTextField = new JTextField();
        mainBot1444TokenLabel = new JLabel();
        mainBot1444TokenTextField = new JTextField();
        startMainBot1444Button = new JButton();
        stopMainBot1444Button = new JButton();
        botSeparator5 = new JSeparator();
        testBot1444Panel = new JPanel();
        testBot1444NameLabel = new JLabel();
        testBot1444NameTextField = new JTextField();
        testBot1444TokenLabel = new JLabel();
        testBot1444TokenTextField = new JTextField();
        startTestBot1444Button = new JButton();
        stopTestBot1444Button = new JButton();
        botSeparator6 = new JSeparator();
        codeBotPanel = new JPanel();
        codeBotNameLabel = new JLabel();
        codeBotNameTextField = new JTextField();
        codeBotTokenLabel = new JLabel();
        codeBotTokenTextField = new JTextField();
        startCodeBotButton = new JButton();
        stopCodeBotButton = new JButton();
        botSeparator2 = new JSeparator();
        botSeparator4 = new JSeparator();
        testBotPanel = new JPanel();
        testBotNameLabel = new JLabel();
        testBotNameTextField = new JTextField();
        testBotTokenLabel = new JLabel();
        testBotTokenTextField = new JTextField();
        startTestBotButton = new JButton();
        stopTestBotButton = new JButton();
        botSeparator3 = new JSeparator();
        codeTestBotPanel = new JPanel();
        codeTestBotNameLabel = new JLabel();
        codeTestBotNameTextField = new JTextField();
        codeTestBotTokenLabel = new JLabel();
        codeTestBotTokenTextField = new JTextField();
        startCodeTestBotButton = new JButton();
        stopCodeTestBotButton = new JButton();
        splitPane1 = new JSplitPane();
        panel2 = new JPanel();
        logScrollPanel = new JScrollPane();
        logTextPane = new JTextPane();
        panel1 = new JPanel();
        replyButton = new JButton();
        reactLogScrollPanel = new JScrollPane();
        reactLogTextPane = new JTextPane();

        //======== this ========
        setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        setMinimumSize(new Dimension(750, 750));
        setIconImage(new ImageIcon(getClass().getResource("/bot.png")).getImage());
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), 10, 10));

        //======== botsPanel ========
        {
            botsPanel.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing
            .border.EmptyBorder(0,0,0,0), "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e",javax.swing.border.TitledBorder
            .CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dialo\u0067",java.
            awt.Font.BOLD,12),java.awt.Color.red),botsPanel. getBorder()))
            ;botsPanel. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.beans.PropertyChangeEvent e
            ){if("borde\u0072".equals(e.getPropertyName()))throw new RuntimeException();}})
            ;
            botsPanel.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), 10, 10));

            //======== resDirPanel ========
            {
                resDirPanel.setBorder(null);
                resDirPanel.setLayout(new GridLayoutManager(1, 4, new Insets(10, 10, 10, 20), 10, 10));

                //---- selectResDirButton ----
                selectResDirButton.setIcon(UIManager.getIcon("Tree.closedIcon"));
                selectResDirButton.addActionListener(e -> selectResDir(e));
                resDirPanel.add(selectResDirButton, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //---- label2 ----
                label2.setText("D:\\SafwahResult_bot\\resources");
                label2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                label2.setBorder(new EmptyBorder(5, 5, 5, 5));
                label2.setHorizontalAlignment(SwingConstants.LEFT);
                resDirPanel.add(label2, new GridConstraints(0, 1, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    new Dimension(500, 30), null, null));

                //---- label3 ----
                label3.setText("\u0627\u062e\u062a\u0631 \u0645\u0648\u0642\u0639 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a");
                label3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                resDirPanel.add(label3, new GridConstraints(0, 2, 1, 2,
                    GridConstraints.ANCHOR_EAST, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    new Dimension(100, 50), null, null));
            }
            botsPanel.add(resDirPanel, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));
            botsPanel.add(resDirSeparator, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

            //======== botSettingsPanel ========
            {
                botSettingsPanel.setBorder(null);
                botSettingsPanel.setLayout(new GridLayoutManager(13, 1, new Insets(10, 10, 10, 10), 10, 10));

                //======== mainBotPanel ========
                {
                    mainBotPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    mainBotPanel.setBorder(null);
                    mainBotPanel.setLayout(new GridLayoutManager(1, 7, new Insets(10, 10, 10, 10), 10, 10));

                    //---- mainBotNameLabel ----
                    mainBotNameLabel.setText("Main Bot Name");
                    mainBotPanel.add(mainBotNameLabel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- mainBotNameTextField ----
                    mainBotNameTextField.setText("SafwaResults_bot");
                    mainBotPanel.add(mainBotNameTextField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- mainBotTokenLabel ----
                    mainBotTokenLabel.setText("Main Bot Token");
                    mainBotPanel.add(mainBotTokenLabel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- mainBotTokenTextField ----
                    mainBotTokenTextField.setText("5510651333:AAFnGs9PpkEX-IAaY9gleR-2P-AVonqtogs");
                    mainBotPanel.add(mainBotTokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startMainBotButton ----
                    startMainBotButton.setText("Start Main Bot");
                    startMainBotButton.setBackground(new Color(0x00cccc));
                    startMainBotButton.setForeground(Color.black);
                    startMainBotButton.addActionListener(e -> startMainBot(e));
                    mainBotPanel.add(startMainBotButton, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopMainBotButton ----
                    stopMainBotButton.setText("Stop Main Bot");
                    stopMainBotButton.setEnabled(false);
                    stopMainBotButton.addActionListener(e -> stopMainBot(e));
                    mainBotPanel.add(stopMainBotButton, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(mainBotPanel, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    new Dimension(750, 50), null, null));
                botSettingsPanel.add(botSeparator, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== mainBot1444Panel ========
                {
                    mainBot1444Panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    mainBot1444Panel.setBorder(null);
                    mainBot1444Panel.setLayout(new GridLayoutManager(1, 7, new Insets(10, 10, 10, 10), 10, 10));

                    //---- mainBot1444NameLabel ----
                    mainBot1444NameLabel.setText("Bot 1444 Name");
                    mainBot1444Panel.add(mainBot1444NameLabel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- mainBot1444NameTextField ----
                    mainBot1444NameTextField.setText("safwah_result_bot");
                    mainBot1444Panel.add(mainBot1444NameTextField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- mainBot1444TokenLabel ----
                    mainBot1444TokenLabel.setText("Bot 1444 Token");
                    mainBot1444Panel.add(mainBot1444TokenLabel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- mainBot1444TokenTextField ----
                    mainBot1444TokenTextField.setText("5904522478:AAEFNYtOoiyXfIZZbxqH628e0TX74pa-VN0");
                    mainBot1444Panel.add(mainBot1444TokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startMainBot1444Button ----
                    startMainBot1444Button.setText("Start  Bot 1444");
                    startMainBot1444Button.setBackground(new Color(0x00cccc));
                    startMainBot1444Button.setForeground(Color.black);
                    startMainBot1444Button.addActionListener(e -> startMainBot1444(e));
                    mainBot1444Panel.add(startMainBot1444Button, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopMainBot1444Button ----
                    stopMainBot1444Button.setText("Stop Bot 1444");
                    stopMainBot1444Button.addActionListener(e -> stopMainBot1444(e));
                    mainBot1444Panel.add(stopMainBot1444Button, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(mainBot1444Panel, new GridConstraints(2, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    new Dimension(750, 50), null, null));
                botSettingsPanel.add(botSeparator5, new GridConstraints(3, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== testBot1444Panel ========
                {
                    testBot1444Panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    testBot1444Panel.setBorder(null);
                    testBot1444Panel.setLayout(new GridLayoutManager(1, 7, new Insets(10, 10, 10, 10), 10, 10));

                    //---- testBot1444NameLabel ----
                    testBot1444NameLabel.setText("Main Bot Name");
                    testBot1444Panel.add(testBot1444NameLabel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- testBot1444NameTextField ----
                    testBot1444NameTextField.setText("safwa_trial_bot");
                    testBot1444Panel.add(testBot1444NameTextField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- testBot1444TokenLabel ----
                    testBot1444TokenLabel.setText("Main Bot Token");
                    testBot1444Panel.add(testBot1444TokenLabel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- testBot1444TokenTextField ----
                    testBot1444TokenTextField.setText("5933750295:AAG1WA5LMr07WgpZj1yuCYWkS0EDKPIBUEY");
                    testBot1444Panel.add(testBot1444TokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startTestBot1444Button ----
                    startTestBot1444Button.setText("Test Bot 1444");
                    startTestBot1444Button.setForeground(Color.black);
                    startTestBot1444Button.setBackground(new Color(0x00cccc));
                    startTestBot1444Button.addActionListener(e -> startTestBot1444(e));
                    testBot1444Panel.add(startTestBot1444Button, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopTestBot1444Button ----
                    stopTestBot1444Button.setText("Stop test Bot 1444");
                    stopTestBot1444Button.addActionListener(e -> stopTestBot1444(e));
                    testBot1444Panel.add(stopTestBot1444Button, new GridConstraints(0, 6, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(testBot1444Panel, new GridConstraints(4, 0, 1, 1,
                    GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    new Dimension(750, 50), null, null));
                botSettingsPanel.add(botSeparator6, new GridConstraints(5, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== codeBotPanel ========
                {
                    codeBotPanel.setBorder(null);
                    codeBotPanel.setLayout(new GridLayoutManager(2, 6, new Insets(10, 10, 10, 10), 10, 10));

                    //---- codeBotNameLabel ----
                    codeBotNameLabel.setText("Code Bot Name");
                    codeBotPanel.add(codeBotNameLabel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- codeBotNameTextField ----
                    codeBotNameTextField.setText("SafwahCode_Bot");
                    codeBotPanel.add(codeBotNameTextField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- codeBotTokenLabel ----
                    codeBotTokenLabel.setText("Code Bot Token");
                    codeBotPanel.add(codeBotTokenLabel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- codeBotTokenTextField ----
                    codeBotTokenTextField.setText("5862702632:AAHAy_OdwpVanxuLGnc5QLjrlasaLhA27_w");
                    codeBotPanel.add(codeBotTokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startCodeBotButton ----
                    startCodeBotButton.setText("Start Code Bot");
                    startCodeBotButton.setBackground(new Color(0x00cccc));
                    startCodeBotButton.setForeground(Color.black);
                    startCodeBotButton.addActionListener(e -> startCodeBot(e));
                    codeBotPanel.add(startCodeBotButton, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopCodeBotButton ----
                    stopCodeBotButton.setText("Stop Code Bot");
                    stopCodeBotButton.setEnabled(false);
                    stopCodeBotButton.addActionListener(e -> stopCodeBot(e));
                    codeBotPanel.add(stopCodeBotButton, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(codeBotPanel, new GridConstraints(6, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
                botSettingsPanel.add(botSeparator2, new GridConstraints(7, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
                botSettingsPanel.add(botSeparator4, new GridConstraints(8, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== testBotPanel ========
                {
                    testBotPanel.setBorder(null);
                    testBotPanel.setLayout(new GridLayoutManager(2, 6, new Insets(10, 10, 10, 10), 10, 10));

                    //---- testBotNameLabel ----
                    testBotNameLabel.setText("Test Bot Name");
                    testBotPanel.add(testBotNameLabel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- testBotNameTextField ----
                    testBotNameTextField.setText("SafwahResultsBeta_bot");
                    testBotPanel.add(testBotNameTextField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- testBotTokenLabel ----
                    testBotTokenLabel.setText("Test Bot Token");
                    testBotPanel.add(testBotTokenLabel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- testBotTokenTextField ----
                    testBotTokenTextField.setText("5376740361:AAEsrMPqWleyGYF3r28ipJCyapQKW6YUtYk");
                    testBotPanel.add(testBotTokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startTestBotButton ----
                    startTestBotButton.setText("Start Test Bot");
                    startTestBotButton.addActionListener(e -> startTestBot(e));
                    testBotPanel.add(startTestBotButton, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopTestBotButton ----
                    stopTestBotButton.setText("Stop Test Bot");
                    stopTestBotButton.setEnabled(false);
                    stopTestBotButton.addActionListener(e -> stopTestBot(e));
                    testBotPanel.add(stopTestBotButton, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(testBotPanel, new GridConstraints(9, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
                botSettingsPanel.add(botSeparator3, new GridConstraints(10, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== codeTestBotPanel ========
                {
                    codeTestBotPanel.setBorder(null);
                    codeTestBotPanel.setLayout(new GridLayoutManager(2, 6, new Insets(10, 10, 10, 10), 10, 10));

                    //---- codeTestBotNameLabel ----
                    codeTestBotNameLabel.setText("Code Test Bot Name");
                    codeTestBotPanel.add(codeTestBotNameLabel, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- codeTestBotNameTextField ----
                    codeTestBotNameTextField.setText("SafwahCodeTest_Bot");
                    codeTestBotPanel.add(codeTestBotNameTextField, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- codeTestBotTokenLabel ----
                    codeTestBotTokenLabel.setText("Code Test Bot Token");
                    codeTestBotPanel.add(codeTestBotTokenLabel, new GridConstraints(0, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- codeTestBotTokenTextField ----
                    codeTestBotTokenTextField.setText("5824154882:AAHsdn5bl7B4jCcXl20KRSw5v-7XIlcbcTI");
                    codeTestBotPanel.add(codeTestBotTokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startCodeTestBotButton ----
                    startCodeTestBotButton.setText("Start Code Test Bot");
                    startCodeTestBotButton.addActionListener(e -> startCodeTestBot(e));
                    codeTestBotPanel.add(startCodeTestBotButton, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopCodeTestBotButton ----
                    stopCodeTestBotButton.setText("Stop Code Test Bot");
                    stopCodeTestBotButton.setEnabled(false);
                    stopCodeTestBotButton.addActionListener(e -> stopCodeTestBot(e));
                    codeTestBotPanel.add(stopCodeTestBotButton, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(codeTestBotPanel, new GridConstraints(11, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
            }
            botsPanel.add(botSettingsPanel, new GridConstraints(2, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null, 0, true));
        }
        contentPane.add(botsPanel, new GridConstraints(0, 0, 1, 2,
            GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //======== splitPane1 ========
        {
            splitPane1.setDividerLocation(450);
            splitPane1.setDividerSize(7);
            splitPane1.setLastDividerLocation(450);

            //======== panel2 ========
            {
                panel2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), 10, 3));

                //======== logScrollPanel ========
                {
                    logScrollPanel.setBorder(null);
                    logScrollPanel.setAutoscrolls(true);

                    //---- logTextPane ----
                    logTextPane.setAutoscrolls(false);
                    logTextPane.setEditable(false);
                    logTextPane.setMargin(new Insets(10, 10, 10, 10));
                    logScrollPanel.setViewportView(logTextPane);
                }
                panel2.add(logScrollPanel, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            splitPane1.setLeftComponent(panel2);

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), 10, 3));

                //---- replyButton ----
                replyButton.setText("text");
                replyButton.addActionListener(e -> reply(e));
                panel1.add(replyButton, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== reactLogScrollPanel ========
                {
                    reactLogScrollPanel.setBorder(null);
                    reactLogScrollPanel.setAutoscrolls(true);

                    //---- reactLogTextPane ----
                    reactLogTextPane.setAutoscrolls(false);
                    reactLogTextPane.setEditable(false);
                    reactLogTextPane.setMargin(new Insets(10, 10, 10, 10));
                    reactLogScrollPanel.setViewportView(reactLogTextPane);
                }
                panel1.add(reactLogScrollPanel, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            splitPane1.setRightComponent(panel1);
        }
        contentPane.add(splitPane1, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
            null, null, null));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - ahmed
    private JPanel botsPanel;
    private JPanel resDirPanel;
    private JButton selectResDirButton;
    private JLabel label2;
    private JLabel label3;
    private JSeparator resDirSeparator;
    private JPanel botSettingsPanel;
    private JPanel mainBotPanel;
    private JLabel mainBotNameLabel;
    private JTextField mainBotNameTextField;
    private JLabel mainBotTokenLabel;
    private JTextField mainBotTokenTextField;
    private JButton startMainBotButton;
    private JButton stopMainBotButton;
    private JSeparator botSeparator;
    private JPanel mainBot1444Panel;
    private JLabel mainBot1444NameLabel;
    private JTextField mainBot1444NameTextField;
    private JLabel mainBot1444TokenLabel;
    private JTextField mainBot1444TokenTextField;
    private JButton startMainBot1444Button;
    private JButton stopMainBot1444Button;
    private JSeparator botSeparator5;
    private JPanel testBot1444Panel;
    private JLabel testBot1444NameLabel;
    private JTextField testBot1444NameTextField;
    private JLabel testBot1444TokenLabel;
    private JTextField testBot1444TokenTextField;
    private JButton startTestBot1444Button;
    private JButton stopTestBot1444Button;
    private JSeparator botSeparator6;
    private JPanel codeBotPanel;
    private JLabel codeBotNameLabel;
    private JTextField codeBotNameTextField;
    private JLabel codeBotTokenLabel;
    private JTextField codeBotTokenTextField;
    private JButton startCodeBotButton;
    private JButton stopCodeBotButton;
    private JSeparator botSeparator2;
    private JSeparator botSeparator4;
    private JPanel testBotPanel;
    private JLabel testBotNameLabel;
    private JTextField testBotNameTextField;
    private JLabel testBotTokenLabel;
    private JTextField testBotTokenTextField;
    private JButton startTestBotButton;
    private JButton stopTestBotButton;
    private JSeparator botSeparator3;
    private JPanel codeTestBotPanel;
    private JLabel codeTestBotNameLabel;
    private JTextField codeTestBotNameTextField;
    private JLabel codeTestBotTokenLabel;
    private JTextField codeTestBotTokenTextField;
    private JButton startCodeTestBotButton;
    private JButton stopCodeTestBotButton;
    private JSplitPane splitPane1;
    private JPanel panel2;
    private JScrollPane logScrollPanel;
    private JTextPane logTextPane;
    private JPanel panel1;
    private JButton replyButton;
    private JScrollPane reactLogScrollPanel;
    private JTextPane reactLogTextPane;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void updateLog(String log, String type) {
        JTextPane logPane = logTextPane;
        if (type.equals("react")) {
            logPane = reactLogTextPane;
        }

        if (type.equals("error")) {
            logPane.setForeground(Color.RED);
        } else {
            logPane.setForeground(Color.BLACK);
        }

        logPane.setText(logPane.getText() + "\n" + log);

        StyledDocument doc = logPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        logPane.setCaretPosition(logPane.getDocument().getLength());
    }
}
