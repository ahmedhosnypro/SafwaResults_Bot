/*
 * Created by JFormDesigner on Tue Jun 14 11:49:45 EET 2022
 */

package com.safwah.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.intellij.uiDesigner.core.*;

/**
 * @author unknown
 */
public class BotPanel extends JPanel {
    public BotPanel() {
        initComponents();
    }

    private void selectResDir(ActionEvent e) {
        // TODO add your code here
    }

    private void startBot(ActionEvent e) {
        // TODO add your code here
    }

    private void startTestBot(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        resDirPanel = new JPanel();
        label3 = new JLabel();
        selectResDirButton = new JButton();
        label2 = new JLabel();
        resDirSeparator = new JSeparator();
        botSettingsPanel = new JPanel();
        botSettingsLabel = new JLabel();
        mainBotPanel = new JPanel();
        mainBotNameLabel = new JLabel();
        mainBotNameTextField = new JTextField();
        mainBotTokenLabel = new JLabel();
        mainBotTokenTextField = new JTextField();
        mainBotControlPanel = new JPanel();
        button2 = new JButton();
        startBotButton = new JButton();
        botSeparator = new JSeparator();
        testBotPanel = new JPanel();
        testBotNameLabel = new JLabel();
        testBotNameTextField = new JTextField();
        testBotTokenLabel = new JLabel();
        testBotTokenTextField = new JTextField();
        testBotControlPanel = new JPanel();
        startTestBotButton = new JButton();
        stopTestBotButton = new JButton();

        //======== this ========
        setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));

        //======== resDirPanel ========
        {
            resDirPanel.setBorder(LineBorder.createBlackLineBorder());
            resDirPanel.setLayout(new GridLayoutManager(2, 2, new Insets(10, 10, 10, 10), -1, -1));

            //---- label3 ----
            label3.setText("\u0627\u062e\u062a\u0631 \u0645\u0648\u0642\u0639 \u0642\u0627\u0639\u062f\u0629 \u0627\u0644\u0628\u064a\u0627\u0646\u0627\u062a");
            label3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            label3.setHorizontalAlignment(SwingConstants.CENTER);
            resDirPanel.add(label3, new GridConstraints(0, 0, 1, 2,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                new Dimension(100, 50), null, null));

            //---- selectResDirButton ----
            selectResDirButton.setIcon(UIManager.getIcon("Tree.closedIcon"));
            selectResDirButton.addActionListener(e -> selectResDir(e));
            resDirPanel.add(selectResDirButton, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

            //---- label2 ----
            label2.setText("D:\\SafwahResult_bot\\resources");
            label2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            label2.setBorder(new EmptyBorder(5, 5, 5, 5));
            label2.setHorizontalAlignment(SwingConstants.LEFT);
            resDirPanel.add(label2, new GridConstraints(1, 1, 1, 1,
                GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, new Dimension(300, 30)));
        }
        add(resDirPanel, new GridConstraints(0, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));
        add(resDirSeparator, new GridConstraints(1, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null));

        //======== botSettingsPanel ========
        {
            botSettingsPanel.setBorder(LineBorder.createBlackLineBorder());
            botSettingsPanel.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));

            //---- botSettingsLabel ----
            botSettingsLabel.setText("\u0627\u0639\u062f\u0627\u062f\u0627\u062a \u0627\u0644\u0628\u0648\u062a");
            botSettingsPanel.add(botSettingsLabel, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== mainBotPanel ========
            {
                mainBotPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                mainBotPanel.setBorder(LineBorder.createBlackLineBorder());
                mainBotPanel.setLayout(new GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));

                //---- mainBotNameLabel ----
                mainBotNameLabel.setText("Main Bot Name");
                mainBotPanel.add(mainBotNameLabel, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //---- mainBotNameTextField ----
                mainBotNameTextField.setText("SafwaResults_bot");
                mainBotPanel.add(mainBotNameTextField, new GridConstraints(0, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //---- mainBotTokenLabel ----
                mainBotTokenLabel.setText("Main Bot Token");
                mainBotPanel.add(mainBotTokenLabel, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //---- mainBotTokenTextField ----
                mainBotTokenTextField.setText("5510651333:AAFnGs9PpkEX-IAaY9gleR-2P-AVonqtogs");
                mainBotPanel.add(mainBotTokenTextField, new GridConstraints(1, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== mainBotControlPanel ========
                {
                    mainBotControlPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

                    //---- button2 ----
                    button2.setText("Stop Main Bot");
                    mainBotControlPanel.add(button2, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- startBotButton ----
                    startBotButton.setText("Start Main Bot");
                    startBotButton.addActionListener(e -> startBot(e));
                    mainBotControlPanel.add(startBotButton, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                mainBotPanel.add(mainBotControlPanel, new GridConstraints(2, 0, 1, 2,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            botSettingsPanel.add(mainBotPanel, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));
            botSettingsPanel.add(botSeparator, new GridConstraints(2, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));

            //======== testBotPanel ========
            {
                testBotPanel.setBorder(LineBorder.createBlackLineBorder());
                testBotPanel.setLayout(new GridLayoutManager(3, 2, new Insets(10, 10, 10, 10), -1, -1));

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
                    null, null, null));

                //---- testBotTokenLabel ----
                testBotTokenLabel.setText("Test Bot Token");
                testBotPanel.add(testBotTokenLabel, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //---- testBotTokenTextField ----
                testBotTokenTextField.setText("5376740361:AAEsrMPqWleyGYF3r28ipJCyapQKW6YUtYk");
                testBotPanel.add(testBotTokenTextField, new GridConstraints(1, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== testBotControlPanel ========
                {
                    testBotControlPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

                    //---- startTestBotButton ----
                    startTestBotButton.setText("Start Test Bot");
                    startTestBotButton.addActionListener(e -> startTestBot(e));
                    testBotControlPanel.add(startTestBotButton, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //---- stopTestBotButton ----
                    stopTestBotButton.setText("Stop Test Bot");
                    testBotControlPanel.add(stopTestBotButton, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                testBotPanel.add(testBotControlPanel, new GridConstraints(2, 0, 1, 2,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            botSettingsPanel.add(testBotPanel, new GridConstraints(3, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED,
                GridConstraints.SIZEPOLICY_FIXED,
                null, null, null));
        }
        add(botSettingsPanel, new GridConstraints(2, 0, 1, 1,
            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
            GridConstraints.SIZEPOLICY_FIXED,
            GridConstraints.SIZEPOLICY_FIXED,
            null, null, null, 0, true));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel resDirPanel;
    private JLabel label3;
    private JButton selectResDirButton;
    private JLabel label2;
    private JSeparator resDirSeparator;
    private JPanel botSettingsPanel;
    private JLabel botSettingsLabel;
    private JPanel mainBotPanel;
    private JLabel mainBotNameLabel;
    private JTextField mainBotNameTextField;
    private JLabel mainBotTokenLabel;
    private JTextField mainBotTokenTextField;
    private JPanel mainBotControlPanel;
    private JButton button2;
    private JButton startBotButton;
    private JSeparator botSeparator;
    private JPanel testBotPanel;
    private JLabel testBotNameLabel;
    private JTextField testBotNameTextField;
    private JLabel testBotTokenLabel;
    private JTextField testBotTokenTextField;
    private JPanel testBotControlPanel;
    private JButton startTestBotButton;
    private JButton stopTestBotButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
