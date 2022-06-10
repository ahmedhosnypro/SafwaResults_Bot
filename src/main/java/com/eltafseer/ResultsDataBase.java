package com.eltafseer;

import kotlin.Pair;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ResultsDataBase {
    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    static HashMap<? extends YearsSubjects, String> getResults(StudyYear studyYear, String nameOrEmail) {
        setDataBaseURL(studyYear.getUrl());

        HashMap<YearsSubjects, String> scores = new HashMap<>();

        YearsSubjects[] subjects = FstYearSubjects.values();
        switch (studyYear) {
            case SND_YEAR -> subjects = SndYearSubjects.values();
            case TRD_YEAR -> subjects = TrdYearSubjects.values();
        }

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
        String getResultsQuery = String.format("SELECT * FROM %s WHERE email = '%s' OR fullName = '%s'", subject.getEnglishName(), nameOrEmail, nameOrEmail);
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultsQuery);
            if (resultSet.next()) {
                return new Pair<>(subject, resultSet.getString("totalScore"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(subject, "0 / غائب");
    }

    private static void setDataBaseURL(String path) {
        dataSource.setUrl(JDBC_URL_PREFIX + path);
    }
}
