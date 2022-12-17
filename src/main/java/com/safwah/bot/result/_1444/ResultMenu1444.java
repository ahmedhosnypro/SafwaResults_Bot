package com.safwah.bot.result._1444;

import com.safwah.study.year.StudyYear;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ResultMenu1444 {
    private ResultMenu1444() {
    }

    public static ReplyKeyboardMarkup Menu() {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        LinkedList<String> buttons = new LinkedList<>();

        buttons.addFirst("الحصول على مساعدة");

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(buttons);
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        keyboard.setKeyboard(keyboardRows);

        return keyboard;
    }
}