package com.safwah.bot.result._1444;

import com.safwah.database.result._1444.ResultsDataBase1444;
import com.safwah.database.year1444.results.CodeCorrector;
import com.safwah.logger.Logger;
import com.safwah.study.year.StudyYear;
import com.safwah.study.year.YearsSubject;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.safwah.bot.result.Results.beautyPrinter;
import static com.safwah.bot.result.Results.intScore;

public class ResultFinder1444 {

    private ResultFinder1444() {
    }

    static void getResults(ResultBot1444 bot, String code, long chatId, String username) {
        if (CodeCorrector.isValidCode(code)) {
            String codePrefix = code.substring(0, 2);
            StudyYear studyYear = switch (codePrefix) {
                case "AD" -> StudyYear.FST_YEAR;
                case "AC" -> StudyYear.SND_YEAR;
                case "AB" -> StudyYear.TRD_YEAR;
                case "AA" -> StudyYear.FTH_YEAR;
                default -> throw new IllegalStateException("Unexpected value: " + codePrefix);
            };
            getResult(bot, studyYear, code, chatId, username);
        } else {
//            bot.sendMessage(header + ResultMessageText1444.NOT_FOUND_EMAIL_ERROR, chatId);
//            Logger.log(messageText, username, header + ResultMessageText.NOT_FOUND_EMAIL_ERROR.name(), "normal");
        }
    }


    static void getResult(ResultBot1444 bot, StudyYear studyYear, String code, long chatId, String username) {
        boolean isMazhab;

        LinkedHashMap<? extends YearsSubject, String> results = ResultsDataBase1444.getResults(studyYear, code);

        if (results.keySet().stream().anyMatch(s ->
                s.getEnglishName().equals("fiqhShafeey") || s.getEnglishName().equals("fiqhHanafy") ||
                        s.getEnglishName().equals("fiqhMalky") || s.getEnglishName().equals("fiqhHanbaly"))) {
            isMazhab = true;
        } else {
            isMazhab = false;
        }

        AtomicReference<Integer> totalScore = new AtomicReference<>(0);
        AtomicInteger subjectCnt = new AtomicInteger();

        String header = studyYear.getArabicNotation() + " | الفصل الدراسي الأول :  " + "\"" + code + "\"\n";

        StringBuilder resultMessage = new StringBuilder(header);
        resultMessage.append("""
                <pre>
                | المادة | الدرجة | النسبة | النجاح |
                                
                                    
                """);
        final boolean[] isMazhabScoreFound = {false};
        LinkedHashMap<YearsSubject, String> mazhabScore = new LinkedHashMap<>();
        results.forEach((subject, score) -> {
            totalScore.updateAndGet(v -> v + intScore(score));
            subjectCnt.getAndIncrement();
            if (isMazhab) {
                if (subject.getEnglishName().equals("fiqhShafeey") ||
                        subject.getEnglishName().equals("fiqhHanafy") ||
                        subject.getEnglishName().equals("fiqhMalky") ||
                        subject.getEnglishName().equals("fiqhHanbaly")) {
                    if (!isMazhabScoreFound[0]) {
                        if (intScore(score) > 0) {
                            isMazhabScoreFound[0] = true;
                            mazhabScore.put(subject, score);
                        }
                    }
                } else {
                    beautyPrinter(subject, score, resultMessage);
                }
            } else {
                beautyPrinter(subject, score, resultMessage);
            }
        });

        resultMessage.append("</pre>\n");

        if (isMazhab && isMazhabScoreFound[0]) {
            beautyPrinter(mazhabScore.keySet().iterator().next(), mazhabScore.values().iterator().next(), resultMessage);
        } else if (isMazhab && !isMazhabScoreFound[0]) {
            resultMessage.append("لم يتم العثور على درجات المذهب");
        }

        int percent;
        if (studyYear == StudyYear.TRD_YEAR || studyYear == StudyYear.FTH_YEAR) {
            percent = (totalScore.get() * 100) / ((subjectCnt.get() - 3) * 50);
        } else {
            percent = (totalScore.get() * 100) / (subjectCnt.get() * 50);
        }
//        resultMessage.append("| النتيجة الكلية").append(" | ").append(totalScore).append(" | ")
//                .append("%").append(percent).append(" | ");


        bot.sendMessage(resultMessage.toString(), chatId);
        Logger.log(code, username, "RESULTS1444", "normal");
    }
}
