package com.safwa.bot;
public class CallBack extends MyTelegramBot {
    void callBack(String callData,  long chatId) {
        if (callData.startsWith("الفرقة")) {
            addNewId(callData, chatId);
        }
    }
}
