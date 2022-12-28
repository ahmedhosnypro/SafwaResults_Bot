package com.safwah.bot.result._1444;

import com.safwah.Student;
import com.safwah.bot.code.bot.CodeFinder;
import com.safwah.database.code.CodeDataBase;
import com.safwah.database.result._1444.ResultsDataBase1444;
import com.safwah.logger.Logger;
import com.safwah.study.year.university.StudyYear1444;
import com.safwah.study.year.YearsSubject;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.safwah.bot.code.corrector.CodeUtilities.formatCode;
import static com.safwah.bot.code.corrector.CodeUtilities.isValidCode;
import static com.safwah.bot.result.Results.beautyPrinter;
import static com.safwah.bot.result.Results.intScore;

public class ResultFinder1444 {

    private ResultFinder1444() {
    }

    static void getResults(ResultBot1444 bot, String input, long chatId, String username) { // input code || name ||  email
        String formattedCode = formatCode(input);
        String codePrefix = "";
        if (formattedCode.length() > 2) {
            codePrefix = formattedCode.substring(0, 2);
        }

        if (isValidCode(formattedCode)) {
            StudyYear1444 studyYear = switch (codePrefix) {
                case "AD" -> StudyYear1444.FST_YEAR;
                case "AC" -> StudyYear1444.SND_YEAR;
                case "AB" -> StudyYear1444.TRD_YEAR;
                case "AA" -> StudyYear1444.FTH_YEAR;
                default -> throw new IllegalStateException("Unexpected value: " + codePrefix);
            };

            //check if the code is in the database
            if (CodeDataBase.isCodeExist(formattedCode)) {
                Student student = CodeFinder.getStudent(formattedCode, studyYear);
                String studentFullName = student.getName().equals("") ? "الاسم غير مسجل" : student.getName();
                getResult(bot, studyYear, studentFullName, formattedCode, chatId, username);
            } else {
                bot.sendMessage("""
                        ❌ هذا الكود غير موجود
                                                
                        تأكد من كتابة الكود بشكل صحيح
                                                
                        هذا رد آلي من جهاز كمبيوتر
                        """, chatId);
            }
        } else {
            String[] codeResult = CodeFinder.tryToGetCode(input, false);
            if (codeResult != null) {
                getResults(bot, codeResult[1], chatId, username);
            } else {
                sendBadRequest(bot, input, chatId, username, StudyYear1444.ERROR);
            }
        }
    }

    private static void sendBadRequest(ResultBot1444 bot, String code, long chatId, String username, StudyYear1444 studyYear) {
        String header = "\"<code>" + code + "</code>\"\n";
        bot.sendMessage(header + ResultMessageText1444.CODE_ERROR, chatId);
        Logger.log(code, username, header + ResultMessageText1444.CODE_ERROR.name(), "normal");
    }


    static void getResult(ResultBot1444 bot, StudyYear1444 studyYear, String name, String code, long chatId, String username) {
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

        String header = studyYear.getArabicNotation() + " -> الفصل: <code> الدراسي الأول </code> :  " + "\n" +
                String.format("""
                        الاسم المسجل بالجامعة : <code> %s </code>
                                                
                        الكود :  👈👈  <code> %s </code>  👉👉
                                                
                        """, name, code);

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
        } else if (isMazhab) {
            resultMessage.append("</pre>\n");
            resultMessage.append("لم يتم العثور على درجات المذهب \uD83D\uDE36");
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

    public static int getTotalScore(StudyYear1444 studyYear, String code) {
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
                }
            }
        });
        return totalScore.get();
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
