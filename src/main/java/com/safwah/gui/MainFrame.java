/*
 * Created by JFormDesigner on Mon Jun 13 16:13:15 EET 2022
 */

package com.safwah.gui;

import javax.swing.event.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.safwah.Main;
import com.safwah.bot.Bot;
import com.safwah.bot.code.CodeBotRunner;
import com.safwah.bot.result.BotRunner;
import com.safwah.logger.Logger;
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

    public Bot getMainBot() {
        return new Bot(mainBotNameTextField.getText(), mainBotTokenTextField.getText());
    }

    public Bot getTestBot() {
        return new Bot(testBotNameTextField.getText(), testBotTokenTextField.getText());
    }

    private void startTestBot(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            BotRunner.runBot(new Bot(testBotNameTextField.getText(), testBotTokenTextField.getText()));
        });

        testBotNameTextField.setEditable(false);
        testBotTokenTextField.setEditable(false);
        startTestBotButton.setEnabled(false);
        stopTestBotButton.setEnabled(true);
    }

    private void stopMainBot(ActionEvent e) {
        BotRunner.stopBot(new Bot(mainBotNameTextField.getText(), mainBotTokenTextField.getText()));
        mainBotNameTextField.setEditable(true);
        mainBotTokenTextField.setEditable(true);
        startMainBotButton.setEnabled(true);
        stopMainBotButton.setEnabled(false);
    }

    private void startMainBot(ActionEvent e) {
        BotRunner.runBot(new Bot(mainBotNameTextField.getText(), mainBotTokenTextField.getText()));
        mainBotNameTextField.setEditable(false);
        mainBotTokenTextField.setEditable(false);
        startMainBotButton.setEnabled(false);
        stopMainBotButton.setEnabled(true);
    }

    private void stopTestBot(ActionEvent ignored) {
        BotRunner.stopBot(new Bot(testBotNameTextField.getText(), testBotTokenTextField.getText()));

        try {
            sleep(500);
        } catch (InterruptedException ex) {
            Logger.log(ex.getMessage());
        }
        testBotNameTextField.setEditable(true);
        testBotTokenTextField.setEditable(true);
        startTestBotButton.setEnabled(true);
        stopTestBotButton.setEnabled(false);
    }

    private void reply(ActionEvent e) {
        // TODO add your code here
    }

    private void startCodeBot(ActionEvent e) {
        CodeBotRunner.runBot(new Bot(codeBotNameTextField.getText(), codeBotTokenTextField.getText()));
        codeBotNameTextField.setEditable(false);
        codeBotTokenTextField.setEditable(false);
        startCodeBotButton.setEnabled(false);
        stopCodeBotButton.setEnabled(true);
    }

    private void stopCodeBot(ActionEvent e) {
        CodeBotRunner.stopBot(new Bot(codeBotNameTextField.getText(), codeBotTokenTextField.getText()));
        codeBotNameTextField.setEditable(true);
        codeBotTokenTextField.setEditable(true);
        startCodeBotButton.setEnabled(true);
        stopCodeBotButton.setEnabled(false);
    }

    private void startCodeTestBot(ActionEvent e) {
        CodeBotRunner.runBot(new Bot(codeTestBotNameTextField.getText(), codeTestBotTokenTextField.getText()));
        codeTestBotNameTextField.setEditable(false);
        codeTestBotTokenTextField.setEditable(false);
        startCodeTestBotButton.setEnabled(false);
        stopCodeTestBotButton.setEnabled(true);
    }

    private void stopCodeTestBot(ActionEvent e) {
        CodeBotRunner.stopBot(new Bot(codeTestBotNameTextField.getText(), codeTestBotTokenTextField.getText()));
        codeTestBotNameTextField.setEditable(true);
        codeTestBotTokenTextField.setEditable(true);
        startCodeTestBotButton.setEnabled(true);
        stopCodeTestBotButton.setEnabled(false);
    }

    private void button2StateChanged(ChangeEvent e) {
        // TODO add your code here
    }

    private void button3(ActionEvent e) {
        // TODO add your code here
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
        testBotPanel = new JPanel();
        testBotNameLabel = new JLabel();
        testBotNameTextField = new JTextField();
        testBotTokenLabel = new JLabel();
        testBotTokenTextField = new JTextField();
        startTestBotButton = new JButton();
        stopTestBotButton = new JButton();
        botSeparator2 = new JSeparator();
        botSeparator4 = new JSeparator();
        codeBotPanel = new JPanel();
        codeBotNameLabel = new JLabel();
        codeBotNameTextField = new JTextField();
        codeBotTokenLabel = new JLabel();
        codeBotTokenTextField = new JTextField();
        startCodeBotButton = new JButton();
        stopCodeBotButton = new JButton();
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
            botsPanel.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new
            javax. swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frm\u0044es\u0069gn\u0065r \u0045va\u006cua\u0074io\u006e", javax
            . swing. border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java
            .awt .Font ("D\u0069al\u006fg" ,java .awt .Font .BOLD ,12 ), java. awt
            . Color. red) ,botsPanel. getBorder( )) ); botsPanel. addPropertyChangeListener (new java. beans.
            PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062or\u0064er" .
            equals (e .getPropertyName () )) throw new RuntimeException( ); }} );
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
                botSettingsPanel.setLayout(new GridLayoutManager(8, 1, new Insets(10, 10, 10, 10), 10, 10));

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
                botSettingsPanel.add(testBotPanel, new GridConstraints(2, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
                botSettingsPanel.add(botSeparator2, new GridConstraints(3, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
                botSettingsPanel.add(botSeparator4, new GridConstraints(4, 0, 1, 1,
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
                    codeBotTokenTextField.setText("5862702632:AAF2dxsSAVuZE7i1sjJ1FqCgQB-5CZgWx78");
                    codeBotPanel.add(codeBotTokenTextField, new GridConstraints(0, 3, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        new Dimension(50, 30), null, null));

                    //---- startCodeBotButton ----
                    startCodeBotButton.setText("Start Code Bot");
                    startCodeBotButton.addActionListener(e -> startCodeBot(e));
                    codeBotPanel.add(startCodeBotButton, new GridConstraints(0, 4, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopCodeBotButton ----
                    stopCodeBotButton.setText("Stop Code Bot");
                    stopCodeBotButton.addActionListener(e -> stopCodeBot(e));
                    codeBotPanel.add(stopCodeBotButton, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(codeBotPanel, new GridConstraints(5, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
                botSettingsPanel.add(botSeparator3, new GridConstraints(6, 0, 1, 1,
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
                    stopCodeTestBotButton.addActionListener(e -> stopCodeTestBot(e));
                    codeTestBotPanel.add(stopCodeTestBotButton, new GridConstraints(0, 5, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                botSettingsPanel.add(codeTestBotPanel, new GridConstraints(7, 0, 1, 1,
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
    private JPanel testBotPanel;
    private JLabel testBotNameLabel;
    private JTextField testBotNameTextField;
    private JLabel testBotTokenLabel;
    private JTextField testBotTokenTextField;
    private JButton startTestBotButton;
    private JButton stopTestBotButton;
    private JSeparator botSeparator2;
    private JSeparator botSeparator4;
    private JPanel codeBotPanel;
    private JLabel codeBotNameLabel;
    private JTextField codeBotNameTextField;
    private JLabel codeBotTokenLabel;
    private JTextField codeBotTokenTextField;
    private JButton startCodeBotButton;
    private JButton stopCodeBotButton;
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

        logPane.setText(logPane.getText() + "\n" + log);

        StyledDocument doc = logPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        logPane.setCaretPosition(logPane.getDocument().getLength());
    }
}
