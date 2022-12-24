package com.safwah.bot.code.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CodeMenu {
    private CodeMenu() {
    }

    public static ReplyKeyboardMarkup codeMenu() {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        LinkedList<String> buttons = new LinkedList<>();

        buttons.addFirst("أدخل اسمك أو بريدك الإلكتروني");

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(buttons);
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        keyboard.setKeyboard(keyboardRows);

        return keyboard;
    }
}