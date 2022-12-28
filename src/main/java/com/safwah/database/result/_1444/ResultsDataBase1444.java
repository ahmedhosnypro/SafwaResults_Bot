package com.safwah.database.result._1444;

import com.safwah.Main;
import com.safwah.logger.Logger;
import com.safwah.study.year.university.StudyYear1444;
import com.safwah.study.year.YearsSubject;
import kotlin.Pair;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import static com.safwah.study.year.university.StudyYear1444.listExaminedSubjects;

public class ResultsDataBase1444 {
    private ResultsDataBase1444() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource fstDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource sndDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource trdDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource fthDataSource = new SQLiteDataSource();
    public static final Connection fstYearConnection;
    public static final Connection sndYearConnection;
    public static final Connection trdYearConnection;
    public static final Connection fthYearConnection;


    static {
        fstDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear1444.FST_YEAR.getDataBaseName());
        sndDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear1444.SND_YEAR.getDataBaseName());
        trdDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear1444.TRD_YEAR.getDataBaseName());
        fthDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear1444.FTH_YEAR.getDataBaseName());
        try {
            fstYearConnection = fstDataSource.getConnection();
            sndYearConnection = sndDataSource.getConnection();
            trdYearConnection = trdDataSource.getConnection();
            fthYearConnection = fthDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedHashMap<? extends YearsSubject, String> getResults(StudyYear1444 studyYear, String code) {

        LinkedHashMap<YearsSubject, String> scores = new LinkedHashMap<>();

        var subjects = listExaminedSubjects(studyYear);

        for (var subject : subjects) {
            Pair<? extends YearsSubject, String> result = getResult(studyYear, subject, code);
            scores.put(subject, result.getSecond());
        }
        return scores;
    }

    private static Pair<? extends YearsSubject, String> getResult(StudyYear1444 studyYear, YearsSubject subject, String code) {
        Connection con = switch (studyYear) {
            case FTH_YEAR -> fthYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case SND_YEAR -> sndYearConnection;
            default -> fstYearConnection;
        };
        return getResults(subject, code, con);
    }

    private static Pair<? extends YearsSubject, String> getResults(YearsSubject subject, String code, Connection con) {
        String getResultQuery = String.format("""
                        SELECT *
                        FROM %s
                        where right_code_2 = '%s'
                           """,
                subject.getEnglishName(),
                code
        );
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            if (resultSet.next()) {
                return new Pair<>(subject, resultSet.getString("totalScore"));
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return new Pair<>(subject, "غ");
    }


}
