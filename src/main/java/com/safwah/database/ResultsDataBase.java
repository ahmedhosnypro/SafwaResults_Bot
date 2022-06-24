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

    public static LinkedHashMap<? extends YearsSubjects, String> getResults(StudyYear studyYear, String nameOrEmail) {
        setDataBaseURL(studyYear.getDB_NAME());

        LinkedHashMap<YearsSubjects, String> scores = new LinkedHashMap<>();

        var subjects = listSubjects(studyYear);

        for (var subject : subjects) {
            var result = getResult(subject, nameOrEmail);
            if (result != null) {
            scores.put(result.getFirst(), result.getSecond());
            }
        }

        return scores;
    }

    private static Pair<? extends YearsSubjects, String> getResult(YearsSubjects subjects, String nameOrEmail) {
        try (Connection con = dataSource.getConnection()) {
            return getResults(subjects, nameOrEmail, con);
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            return null;
        }
    }

    /*
    replace(replace
                  (replace(fullName, 'أ', 'ا'), 'إ', 'ا'), 'آ', 'ا')
     */

    private static Pair<? extends YearsSubjects, String> getResults(YearsSubjects subject, String nameOrEmail, Connection con) {
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
                           """, subject.getEnglishName(),
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
        setDataBaseURL(studyYear.getDB_NAME());
        List<? extends YearsSubjects> subjects = listSubjects(studyYear);
        try (Connection con = dataSource.getConnection()) {
            for (var subject : subjects) {
                if (isNotExists(subject, nameOrEmail, con)) {
                    return false;
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

    private static boolean isNotExists(YearsSubjects subject, String nameOrEmail, Connection con) {
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
                           """, subject.getEnglishName(),
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
