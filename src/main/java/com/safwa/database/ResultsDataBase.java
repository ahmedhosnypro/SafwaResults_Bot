package com.safwa.database;

import com.safwa.study.year.YearsSubjects;
import com.safwa.study.year.FstYearSubjects;
import com.safwa.study.year.SndYearSubjects;
import com.safwa.study.year.StudyYear;
import com.safwa.study.year.TrdYearSubjects;
import kotlin.Pair;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ResultsDataBase {
    private ResultsDataBase() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    public static LinkedHashMap<? extends YearsSubjects, String> getResults(StudyYear studyYear, String nameOrEmail) {
        setDataBaseURL(studyYear.getUrl());

        LinkedHashMap<YearsSubjects, String> scores = new LinkedHashMap<>();

        var subjects = listSubjects(studyYear);

        for (var subject : subjects) {
            var result = getResult(subject, nameOrEmail);
            scores.put(result.getFirst(), result.getSecond());
        }

        return scores;
    }

    private static Pair<? extends YearsSubjects, String> getResult(YearsSubjects subjects, String nameOrEmail) {
        try (Connection con = dataSource.getConnection()) {
            return getResults(subjects, nameOrEmail, con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Pair<? extends YearsSubjects, String> getResults(YearsSubjects subject, String nameOrEmail, Connection con) {
        String lowerCaseEmail = nameOrEmail.toUpperCase();
        String getResultQuery = "SELECT * FROM " + subject.getEnglishName() +
                " WHERE  lower(email) LIKE '%" + lowerCaseEmail + "%' OR fullName LIKE '%" + nameOrEmail + "%'";
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
        var jarPath = ResultsDataBase.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        String jarParentPath = new File(jarPath).getAbsolutePath();
        if (jarParentPath.endsWith("\\SafwaResults.exe")) {
            jarParentPath = jarParentPath.replace("\\SafwaResults.exe", "");
        }
        dataSource.setUrl(JDBC_URL_PREFIX + jarParentPath + "\\" + path);
    }

    public static boolean isNotExists(StudyYear studyYear, String nameOrEmail) {
        setDataBaseURL(studyYear.getUrl());
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
        String upperCaseEmail = nameOrEmail.toUpperCase().trim();
        String isExistQuery = "SELECT * FROM " + subject.getEnglishName() +
                " WHERE  upper(email) LIKE '%" + upperCaseEmail + "%' OR fullName LIKE '%" + nameOrEmail + "%'";
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
