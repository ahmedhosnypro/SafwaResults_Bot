package com.eltafseer;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CommandProcessor extends SafwaResultsBot {
    void processCommand(String messageText, long chatId) {
        if (messageText != null && !messageText.isBlank() && !messageText.isEmpty()) {
            if (messageText.equals("/start")) {
                printStudyYears(chatId, "اختر الفرقة الدراسية");
                Logger.logSuccess("اختر الفرقة الدراسية");
            } else if (messageText.startsWith("الفرقة")) {
                addNewId(messageText, chatId);
            } else {
                getResults(messageText, chatId);
            }
        }
    }

    void printStudyYears(long chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void addNewId(String messageText, long chatId) {
        if (IdYearDataBase.setIdYear(chatId, messageText)) {
            sendMessage("اكتب اسمك رباعيا أو بريدك الإلكتروني", chatId);
            Logger.logSuccess("اكتب اسمك رباعيا أو بريدك الإلكتروني");
        } else {
            sendYearsWithFeedBack("حاول مجددا", chatId);
        }
    }

    void sendMessage(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setParseMode(ParseMode.HTML);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void sendYearsWithFeedBack(String messageText, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(messageText);
        message.setReplyMarkup(Menu.studyYearsMenu());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    void getResults(String messageText, long chatId) {
        StudyYear studyYear = IdYearDataBase.getStudyYear(chatId);
        if (studyYear != StudyYear.ERROR) {
            var results = ResultsDataBase.getResults(studyYear, messageText);
            AtomicReference<Integer> totalScore = new AtomicReference<>(0);
            AtomicInteger subjectCnt = new AtomicInteger();


            StringBuilder resultMessage = new StringBuilder();
            resultMessage.append("<pre>").append("""
                    |   الـــمـــادة  | الدرجة | النسبـــة |    النجاح |
                    ا------------------ |---------|------------|------------|
                                        
                    """);
            results.forEach((subject, score) -> {
                totalScore.updateAndGet(v -> v + intScore(score));
                subjectCnt.getAndIncrement();
                beautyPrinter(subject, score, resultMessage);
            });
            resultMessage.append("</pre>\n\n");

            int percent = 0;
            if (studyYear == StudyYear.SND_YEAR || studyYear == StudyYear.TRD_YEAR) {
                percent = (totalScore.get() * 100) / ((subjectCnt.get() - 3) * 50);
            } else {
                percent = (totalScore.get() * 100) / (subjectCnt.get() * 50);
            }
            resultMessage.append("النتيجة الكلية| ").append(" | ").append(totalScore).append(" | ")
                    .append("%").append(percent).append(" | ");
            sendMessage(resultMessage.toString(), chatId);
            Logger.logSuccess("-----------------" +
                    messageText + "-----------------"
                    + "\n" + resultMessage);
        } else {
            printStudyYears(chatId, """
                    اختر الفرقة الدراسية أولاً أو أرسل التالي اذا لم تظهر قائمة الفرق:
                    الفرقة الأولى
                    الفرقة الثانية
                    الفرقة الثالثة
                    ....
                    """);
        }
    }

    void beautyPrinter(YearsSubjects subject, String score, StringBuilder resultMessage) {
        int intScore = 0;
        int percent = 0;
        boolean success = false;
        try {
            String s = score.replaceAll("\\s+", "").split("/")[0].split("[.]")[0];
            intScore = intScore(score);

            percent = (intScore * 100) / 50;
            success = percent >= 50;
        } catch (Exception e) {
        }
        resultMessage.append(" ا").append(subject.getArabicName()).append(" | ")
                .append(intScore).append(" | ")
                .append("%").append(percent).append(" | ")
                .append(success ? "ناجح" : "راسب").append(" | ").append("\n");
    }

    int intScore(String score) {
        int intScore = 0;
        try {
            String s = score.replaceAll("\\s+", "").split("/")[0].split("[.]")[0];
            intScore = Integer.parseInt(s);
        } catch (Exception e) {
        }
        return intScore;
    }
}
