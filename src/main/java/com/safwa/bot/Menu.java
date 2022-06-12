package com.safwa.bot;

import com.safwa.study.year.StudyYear;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Menu {
    private Menu() {
    }

    public static ReplyKeyboardMarkup studyYearsMenu() {

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        LinkedList<String> buttons = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            buttons.addFirst(StudyYear.getByOrdinal(i).getArabicNotation());
        }

        buttons.addFirst("لدي مشكلة");
        buttons.addFirst("موعد اختبارات الإعادة");

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(buttons);
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        keyboard.setKeyboard(keyboardRows);

        return keyboard;
    }
}