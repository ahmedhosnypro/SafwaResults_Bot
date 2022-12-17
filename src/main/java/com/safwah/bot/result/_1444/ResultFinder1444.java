package com.safwah.bot.result._1444;

import com.safwah.bot.code.CodeFinder;
import com.safwah.database.result._1444.ResultsDataBase1444;
import com.safwah.database.year1444.results.CodeCorrector;
import com.safwah.logger.Logger;
import com.safwah.study.year.StudyYear1444;
import com.safwah.study.year.YearsSubject;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.safwah.bot.result.Results.beautyPrinter;
import static com.safwah.bot.result.Results.intScore;

public class ResultFinder1444 {

    private ResultFinder1444() {
    }

    static void getResults(ResultBot1444 bot, String code, long chatId, String username) {
        code = CodeCorrector.formatCode(code);
        String codePrefix = "";
        if (code.length() > 2) {
            codePrefix = code.substring(0, 2);
        }

        if (CodeCorrector.isValidCode(code)) {
            StudyYear1444 studyYear = switch (codePrefix) {
                case "AD" -> StudyYear1444.FST_YEAR;
                case "AC" -> StudyYear1444.SND_YEAR;
                case "AB" -> StudyYear1444.TRD_YEAR;
                case "AA" -> StudyYear1444.FTH_YEAR;
                default -> throw new IllegalStateException("Unexpected value: " + codePrefix);
            };
            getResult(bot, studyYear, code, chatId, username);
        } else {
            code = CodeFinder.getCode(code);
            if (code != null && !code.equals("")) {
                getResults(bot, code, chatId, username);
            } else {
                sendBadRequest(bot, code, chatId, username, StudyYear1444.ERROR);
            }
        }
    }

    private static void sendBadRequest(ResultBot1444 bot, String code, long chatId, String username, StudyYear1444 studyYear) {
        String header = "\"" + code + "\"\n";
        bot.sendMessage(header + ResultMessageText1444.CODE_ERROR, chatId);
        Logger.log(code, username, header + ResultMessageText1444.CODE_ERROR.name(), "normal");
    }


    static void getResult(ResultBot1444 bot, StudyYear1444 studyYear, String code, long chatId, String username) {
        boolean isMazhab;
        StudyYear1444 inputStudyYear = studyYear;

        LinkedHashMap<? extends YearsSubject, String> results = ResultsDataBase1444.getResults(studyYear, code);

        if (studyYear != StudyYear1444.FST_YEAR) {
            while (results.values().stream().allMatch(s -> intScore(s) == 0) && studyYear != StudyYear1444.FST_YEAR) {
                studyYear = switch (studyYear) {
                    case FTH_YEAR -> StudyYear1444.TRD_YEAR;
                    case TRD_YEAR -> StudyYear1444.SND_YEAR;
                    default -> StudyYear1444.FST_YEAR;
                };
                results = ResultsDataBase1444.getResults(studyYear, code);
            }
            if (results.values().stream().allMatch(s -> intScore(s) == 0) && inputStudyYear != studyYear) {
                studyYear = inputStudyYear;
                results = ResultsDataBase1444.getResults(studyYear, code);
            }
        }

        if (results.keySet().stream().anyMatch(s ->
                s.getEnglishName().equals("fiqhShafeey") || s.getEnglishName().equals("fiqhHanafy") ||
                        s.getEnglishName().equals("fiqhMalky") || s.getEnglishName().equals("fiqhHanbaly"))) {
            isMazhab = true;
        } else {
            isMazhab = false;
        }

        AtomicReference<Integer> totalScore = new AtomicReference<>(0);
        AtomicInteger subjectCnt = new AtomicInteger();

        String header = "يجب استخدام الكود التالي في نموذج الاختبار يمكنك الضغط عليه وسيتم نسخه الى الحافظة: " +
                "\n\uD83D\uDC49\uD83D\uDC49  <code>" + code + "</code>  \uD83D\uDC48\uD83D\uDC48\n" +
                studyYear.getArabicNotation() + " | الفصل الدراسي الأول :  " + "\n";

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


        if (isMazhab && isMazhabScoreFound[0]) {
            beautyPrinter(mazhabScore.keySet().iterator().next(), mazhabScore.values().iterator().next(), resultMessage);
            resultMessage.append("</pre>\n");
        } else if (isMazhab && !isMazhabScoreFound[0]) {
            resultMessage.append("</pre>\n");
            resultMessage.append("لم يتم العثور على درجات المذهب");
        } else {
            resultMessage.append("</pre>\n");
        }

//        int percent;
//        if (studyYear == StudyYear1444.TRD_YEAR || studyYear == StudyYear1444.FTH_YEAR) {
//            percent = (totalScore.get() * 100) / ((subjectCnt.get() - 3) * 50);
//        } else {
//            percent = (totalScore.get() * 100) / (subjectCnt.get() * 50);
//        }
//        resultMessage.append("| النتيجة الكلية").append(" | ").append(totalScore).append(" | ")
//                .append("%").append(percent).append(" | ");

        resultMessage.append("..");
        bot.sendMessage(resultMessage.toString(), chatId);
        Logger.log(code, username, "RESULTS1444", "normal");

        if (studyYear != StudyYear1444.FST_YEAR) {
            switch (studyYear) {
                case FTH_YEAR -> {
                    getLastResults(bot, StudyYear1444.TRD_YEAR, code, chatId, username);
                    getLastResults(bot, StudyYear1444.SND_YEAR, code, chatId, username);
                    getLastResults(bot, StudyYear1444.FST_YEAR, code, chatId, username);
                }
                case TRD_YEAR -> {
                    getLastResults(bot, StudyYear1444.SND_YEAR, code, chatId, username);
                    getLastResults(bot, StudyYear1444.FST_YEAR, code, chatId, username);
                }
                case SND_YEAR -> getLastResults(bot, StudyYear1444.FST_YEAR, code, chatId, username);
                default -> throw new IllegalStateException("Unexpected value: " + studyYear);
            }
        }
    }

    static void getLastResults(ResultBot1444 bot, StudyYear1444 studyYear, String code, long chatId, String username) {
        LinkedHashMap<? extends YearsSubject, String> results = ResultsDataBase1444.getResults(studyYear, code);
        if (results.values().stream().allMatch(s -> intScore(s) == 0)) {
            return;
        }

        AtomicReference<Integer> totalScore = new AtomicReference<>(0);
        AtomicInteger subjectCnt = new AtomicInteger();

        String header = studyYear.getArabicNotation() + " | الفصل الدراسي الأول :  " + "\"" + code + "\"\n";

        StringBuilder resultMessage = new StringBuilder(header);
        resultMessage.append("""
                <pre>
                | المادة | الدرجة | النسبة | النجاح |


                """);


        results.forEach((subject, score) -> {
            totalScore.updateAndGet(v -> v + intScore(score));
            subjectCnt.getAndIncrement();
            if (intScore(score) > 0) {
                beautyPrinter(subject, score, resultMessage);
            }
        });
        resultMessage.append("</pre>\n");

        resultMessage.append("..");
        bot.sendMessage(resultMessage.toString(), chatId);
        Logger.log(code, username, "RESULTS1444", "normal");
    }
}
