package com.safwah.bot.result._1443;

import com.safwah.database.result._1443.IdYearDataBase;
import com.safwah.database.result._1443.ResultsDataBase;
import com.safwah.logger.Logger;
import com.safwah.study.year.StudyYear;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.safwah.bot.result.Results.beautyPrinter;
import static com.safwah.bot.result.Results.intScore;

public class ResultFinder {

    private ResultFinder() {
    }

    static final String EMAIL_REGEX = "(?:[a-z\\d!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z\\d!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z\\d](?:[a-z\\d-]*[a-z\\d])?\\.)+[a-z\\d](?:[a-z\\d-]*[a-z\\d])?|\\[(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?|[a-z\\d-]*[a-z\\d]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    static void getResults(ResultBot bot, String messageText, long chatId, String username) {
        StudyYear studyYear = IdYearDataBase.getStudyYear(chatId);

        // select the year
        if (studyYear == StudyYear.ERROR) {
            bot.inlineMarkupTitled(ResultMessageText.SELECT_YEAR.toString(), chatId);
            Logger.log(messageText, username, ResultMessageText.SELECT_YEAR.name(), "normal");
            return;
        }

        if (checkNameOrEmail(bot, messageText, chatId, studyYear, username)) {
            getResult(bot, messageText, chatId, studyYear, "fst", username);
            getResult(bot, messageText, chatId, studyYear, "snd", username);
        }
    }

    static void getResult(ResultBot bot, String messageText, long chatId, StudyYear studyYear, String term, String username) {
        var results = ResultsDataBase.getResults(studyYear, term, messageText);
        AtomicReference<Integer> totalScore = new AtomicReference<>(0);
        AtomicInteger subjectCnt = new AtomicInteger();

        String header = switch (term) {
            case "fst" -> studyYear.getArabicNotation() + " | الفصل الدراسي الأول :  " + "\"" + messageText + "\"\n";
            case "snd" -> studyYear.getArabicNotation() + " | الفصل الدراسي الثاني :  " + "\"" + messageText + "\"\n";
            default -> "";
        };

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
        if (studyYear == StudyYear.TRD_YEAR || studyYear == StudyYear.FTH_YEAR) {
            percent = (totalScore.get() * 100) / ((subjectCnt.get() - 3) * 50);
        } else {
            percent = (totalScore.get() * 100) / (subjectCnt.get() * 50);
        }
        resultMessage.append("| النتيجة الكلية").append(" | ").append(totalScore).append(" | ")
                .append("%").append(percent).append(" | ");

        if (studyYear == StudyYear.SND_YEAR && term.equals("fst")) {
            resultMessage.append("<pre>\n").
                    append("قد لا تظهر نتيجة مادتي العقيدة والتجويد لبعض الطلاب المتختبرين على الموقع\n")
                    .append("فاذا كانت درجتك اعلى من 24 درجة فيهما على الموقع فانت ناجح في المادتين").append("\n")
                    .append("بغض النظر عن النتيجة المعروضة هنا ").append("</pre>\n");
        }

        bot.sendMessage(resultMessage.toString(), chatId);
        Logger.log(messageText, username, "RESULTS", "normal");
    }

    static boolean checkNameOrEmail(ResultBot bot, String messageText, long chatId, StudyYear studyYear, String username) {
        boolean isEmail = isEmail(messageText);
        String header = studyYear.getArabicNotation() + ":  \"" + messageText + "\"\n";
        if (isEmail && isInValidEmail(messageText)) {
            bot.sendMessage("\"" + messageText + "\"\n" + ResultMessageText.EMAIL_ERROR, chatId);
            Logger.log(messageText, username, ResultMessageText.EMAIL_ERROR.name(), "normal");
            return false;
        } else if (!isEmail) {
            var arr = messageText.split(" ");
            if (arr.length < 3) {
                bot.sendMessage(header + ResultMessageText.SHORT_NAME_ERROR, chatId);
                Logger.log(messageText, username, header + ResultMessageText.SHORT_NAME_ERROR.name(), "normal");
                return false;
            }

            if (ResultsDataBase.isNotExists(studyYear, messageText)) {
                bot.sendMessage(header + ResultMessageText.NOT_FOUND_NAME_ERROR, chatId);
                Logger.log(messageText, username, header + ResultMessageText.NOT_FOUND_NAME_ERROR.name(), "react");
                return false;
            }
        } else {
            if (ResultsDataBase.isNotExists(studyYear, messageText)) {
                bot.sendMessage(header + ResultMessageText.NOT_FOUND_EMAIL_ERROR, chatId);
                Logger.log(messageText, username, header + ResultMessageText.NOT_FOUND_EMAIL_ERROR.name(), "normal");
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
