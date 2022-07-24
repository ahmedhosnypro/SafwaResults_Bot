package com.safwah.database;

import com.safwah.Main;
import com.safwah.logger.Logger;
import com.safwah.study.year.StudyYear;
import com.safwah.study.year.YearsSubjects;
import com.safwah.study.year.*;
import kotlin.Pair;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

public class ResultsDataBase {
    private ResultsDataBase() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    public static LinkedHashMap<? extends YearsSubjects, String> getResults(StudyYear studyYear, String term, String nameOrEmail) {
        setDataBaseURL(studyYear.getDataBaseName());

        LinkedHashMap<YearsSubjects, String> scores = new LinkedHashMap<>();

        var subjects = listSubjects(studyYear, term);

        switch (term) {
            case "fst" -> term = "1";
            case "snd" -> term = "2";
        }
        for (var subject : subjects) {
            var result = getResult(subject, term, "1", nameOrEmail);
            if (result != null) {
                fstAndSntStageResult(term, nameOrEmail, scores, subject, result);
            } else {
                result = getResult(subject, term, "2", nameOrEmail);
                if (result != null) {
                    scores.put(subject, result.getSecond());
                }
            }
        }

        return scores;
    }

    private static void fstAndSntStageResult(String term, String nameOrEmail, LinkedHashMap<YearsSubjects, String> scores, YearsSubjects subject, Pair<? extends YearsSubjects, String> result) {
        int score = 0;
        int fullScore = 0;
        try {
            var s = result.getSecond().replaceAll("\\s+", "").split("/");
            score = Integer.parseInt(s[0].split("[.]")[0]);
            fullScore = Integer.parseInt(s[1]);
        } catch (Exception e) {
           // ignore
        }
        if (score < fullScore / 2 || score == 0) {
            var sndStageResult = getResult(subject, term, "2", nameOrEmail);
            if (sndStageResult != null) {
                int sndScore = 0;
                try {
                    var s = result.getSecond().replaceAll("\\s+", "").split("/");
                    sndScore = Integer.parseInt(s[0].split("[.]")[0]);
                    fullScore = Integer.parseInt(s[1]);
                } catch (Exception e) {
                    // ignore
                }
                if (sndScore < score) {
                    scores.put(subject, sndStageResult.getSecond());
                } else {
                    scores.put(subject, result.getSecond());
                }
            }
        }else {
            scores.put(subject, result.getSecond());
        }
    }

    private static Pair<? extends YearsSubjects, String> getResult(YearsSubjects subjects, String term, String stage, String nameOrEmail) {
        try (Connection con = dataSource.getConnection()) {
            return getResults(subjects, term, stage, nameOrEmail, con);
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Pair<? extends YearsSubjects, String> getResults(YearsSubjects subject, String term, String stage, String nameOrEmail, Connection con) {
        String getResultQuery = String.format("""
                        SELECT *
                        FROM %s
                        where trim(
                                      replace(
                                              replace(
                                                      replace(
                                                              replace(
                                                                      replace(
                                                                              replace(
                                                                                      replace(
                                                                                              replace(fullName, 'إ', 'ا'),
                                                                                              'أ', 'ا'),
                                                                                      'ى', 'ي'),
                                                                              'ة', 'ه'),
                                                                      'ؤ', 'و'),
                                                              'آ', 'ا'),
                                                      '  ', ' '),
                                              '   ', ' ')) like '%%%s%%'
                           OR lower(email) LIKE '%%%s%%'
                           """, subject.getEnglishName() + term + "_" + stage,
                nameOrEmail.replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه"),
                nameOrEmail.toLowerCase()
        );
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            if (resultSet.next()) {
                return new Pair<>(subject, resultSet.getString("totalScore"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(subject, "0 / غائب");
    }

    private static void setDataBaseURL(String path) {
        dataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + path);
        Logger.log(Main.getResourcePath() + Main.getResourcePath() + path);
    }

    public static boolean isNotExists(StudyYear studyYear, String nameOrEmail) {
        setDataBaseURL(studyYear.getDataBaseName());

        List<? extends YearsSubjects> subjects = listSubjects(studyYear);
        try (Connection con = dataSource.getConnection()) {
            for (var subject : subjects) {
                switch (subject.getTerm()) {
                    case "fst" -> {
                        if (isNotExists(subject, "1", "1", nameOrEmail, con) ||
                                isNotExists(subject, "1", "2", nameOrEmail, con)) {
                            return false;
                        }
                    }
                    case "snd" -> {
                        if (isNotExists(subject, "2", "1", nameOrEmail, con) ||
                                isNotExists(subject, "2", "2", nameOrEmail, con)) {
                            return false;
                        }
                    }
                    case "both" -> {
                        if (isNotExists(subject, "1", "1", nameOrEmail, con) ||
                                isNotExists(subject, "1", "2", nameOrEmail, con) ||
                                isNotExists(subject, "2", "1", nameOrEmail, con) ||
                                isNotExists(subject, "2", "2", nameOrEmail, con)) {
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            return true;
        }
        return true;
    }

    // todo move to enum class
    private static List<? extends YearsSubjects> listSubjects(StudyYear studyYear) {
        List<? extends YearsSubjects> subjects;
        subjects = switch (studyYear) {
            case SND_YEAR ->
                    Arrays.stream(SndYearSubjects.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
            case TRD_YEAR ->
                    Arrays.stream(TrdYearSubjects.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
            default -> Arrays.stream(FstYearSubjects.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
        };
        return subjects;
    }

    private static List<? extends YearsSubjects> listSubjects(StudyYear studyYear, String term) {
        List<? extends YearsSubjects> subjects;
        subjects = switch (studyYear) {
            case SND_YEAR -> Arrays.stream(SndYearSubjects.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case TRD_YEAR -> Arrays.stream(TrdYearSubjects.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            default -> Arrays.stream(FstYearSubjects.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
        };
        return subjects;
    }

    private static boolean isNotExists(YearsSubjects subject, String term, String stage, String nameOrEmail, Connection con) {
        String isExistQuery = String.format("""
                        SELECT *
                        FROM %s
                        where trim(
                                      replace(
                                              replace(
                                                      replace(
                                                              replace(
                                                                      replace(
                                                                              replace(
                                                                                      replace(
                                                                                              replace(fullName, 'إ', 'ا'),
                                                                                              'أ', 'ا'),
                                                                                      'ى', 'ي'),
                                                                              'ة', 'ه'),
                                                                      'ؤ', 'و'),
                                                              'آ', 'ا'),
                                                      '  ', ' '),
                                              '   ', ' ')) like '%%%s%%'
                           OR lower(email) LIKE '%%%s%%'
                           """, subject.getEnglishName() + term + "_" + stage,
                nameOrEmail.replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه"),
                nameOrEmail.toLowerCase()
        );
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(isExistQuery);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
