package com.safwa.bot;

import com.safwa.database.IdYearDataBase;
import com.safwa.database.ResultsDataBase;
import com.safwa.logger.Logger;
import com.safwa.study.year.StudyYear;
import com.safwa.study.year.YearsSubjects;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultFinder {

    private ResultFinder() {
    }

    static final String EMAIL_REGEX = "(?:[a-z\\d!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z\\d!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z\\d](?:[a-z\\d-]*[a-z\\d])?\\.)+[a-z\\d](?:[a-z\\d-]*[a-z\\d])?|\\[(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?|[a-z\\d-]*[a-z\\d]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static void getResults(MyTelegramBot bot, String messageText, long chatId) {
        StudyYear studyYear = IdYearDataBase.getStudyYear(chatId);

        if (studyYear == StudyYear.ERROR) {
            bot.inlineMarkupTitled(MessageText.SELECT_YEAR.toString(), chatId);
            Logger.log(messageText, chatId, MessageText.SELECT_YEAR.name());
            return;
        }

        if (checkNameOrEmail(bot, messageText, chatId, studyYear)) {
            getResult(bot, messageText, chatId, studyYear);
        }
    }

    static void getResult(MyTelegramBot bot, String messageText, long chatId, StudyYear studyYear) {
        var results = ResultsDataBase.getResults(studyYear, messageText);
        AtomicReference<Integer> totalScore = new AtomicReference<>(0);
        AtomicInteger subjectCnt = new AtomicInteger();

        String header = studyYear.getArabicNotation() + ":  \"" + messageText + "\"\n";
        StringBuilder resultMessage = new StringBuilder(header);
        resultMessage.append("""
                <pre>
                | المادة | الدرجة | النسبة | النجاح |
                ا---------- |-------|---------|---------|
                                    
                """);
        results.forEach((subject, score) -> {
            totalScore.updateAndGet(v -> v + intScore(score));
            subjectCnt.getAndIncrement();
            beautyPrinter(subject, score, resultMessage);
        });


        resultMessage.append("</pre>\n");

        int percent;
        if (studyYear == StudyYear.SND_YEAR || studyYear == StudyYear.TRD_YEAR) {
            percent = (totalScore.get() * 100) / ((subjectCnt.get() - 3) * 50);
        } else {
            percent = (totalScore.get() * 100) / (subjectCnt.get() * 50);
        }
        resultMessage.append("| النتيجة الكلية").append(" | ").append(totalScore).append(" | ")
                .append("%").append(percent).append(" | ");

        bot.sendMessage(resultMessage.toString(), chatId);
        Logger.log(messageText, chatId, resultMessage.toString());
    }

    static void beautyPrinter(YearsSubjects subject, String score, StringBuilder resultMessage) {
        int intScore = 0;
        int percent = 0;
        boolean success = false;
        try {
            intScore = intScore(score);

            percent = (intScore * 100) / 50;
            success = percent >= 50;
        } catch (Exception ignored) {
            //ignored
        }
        resultMessage.append(" |").append(subject.getArabicName()).append(" | ")
                .append(intScore).append(" | ")
                .append("%").append(percent).append(" | ")
                .append(success ? "ناجح" : "راسب").append(" | ").append("\n\n");
    }

    static int intScore(String score) {
        int intScore = 0;
        try {
            String s = score.replaceAll("\\s+", "").split("/")[0].split("[.]")[0];
            intScore = Integer.parseInt(s);
        } catch (Exception ignored) {
            // ignored
        }
        return intScore;
    }

    static boolean checkNameOrEmail(MyTelegramBot bot, String messageText, long chatId, StudyYear studyYear) {
        boolean isEmail = isEmail(messageText);
        String header = studyYear.getArabicNotation() + ":  \"" + messageText + "\"\n";
        if (isEmail && isInValidEmail(messageText)) {
            bot.sendMessage("\"" + messageText + "\"\n" + MessageText.EMAIL_ERROR, chatId);
            Logger.log(messageText, chatId, MessageText.EMAIL_ERROR.name());
            return false;
        } else if (!isEmail) {
            var arr = messageText.split(" ");
            if (arr.length < 3) {
                bot.sendMessage(header + MessageText.SHORT_NAME_ERROR, chatId);
                Logger.log(messageText, chatId, header + MessageText.SHORT_NAME_ERROR.name());
                return false;

                // ToDO .name()
            }

            if (ResultsDataBase.isNotExists(studyYear, messageText)) {
                bot.sendMessage(header + MessageText.NOT_FOUND_NAME_ERROR, chatId);
                Logger.log(messageText, chatId, header + MessageText.NOT_FOUND_NAME_ERROR.name());
                return false;
            }
        } else {
            if (ResultsDataBase.isNotExists(studyYear, messageText)) {
                bot.sendMessage(header + MessageText.NOT_FOUND_EMAIL_ERROR, chatId);
                Logger.log(messageText, chatId, header + MessageText.NOT_FOUND_EMAIL_ERROR.name());
                return false;
            }
        }
        return true;
    }

    static boolean isEmail(String message) {
        return message.contains("@");
    }

    private static boolean isInValidEmail(String email) {
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email.toLowerCase());
        return !emailMatcher.matches();
    }
}
