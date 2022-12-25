package com.safwah.bot.code.corrector;

import com.safwah.Main;
import com.safwah.Person;
import com.safwah.study.year.StudyYear1444;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.safwah.study.year.StudyYear1444.*;

public class DataBaseEditor {
    static String fstYearDb = "1444_fst_results.db";
    static String sndYearDb = "1444_snd_results.db";
    static String trdYearDb = "1444_trd_results.db";
    static String fthYearDb = "1444_fth_results.db";
    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";
    private static final SQLiteDataSource fstDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource sndDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource trdDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource fthDataSource = new SQLiteDataSource();
    public static final Connection fstYearConnection;
    public static final Connection sndYearConnection;
    public static final Connection trdYearConnection;
    public static final Connection fthYearConnection;

    static Map<String, Map<String, Map<String, List<Person>>>> repeatedCodesJson = new LinkedHashMap<>();
    static Map<String, Map<String, Map<String, List<Person>>>> repeatedCorrectedCodesJson = new LinkedHashMap<>();
    static List<String> currentSubjectCodes = new ArrayList<>();
    static List<String> currentSubjectCorrectedCodes = new ArrayList<>();


    static {
        fstDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + fstYearDb);
        sndDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + sndYearDb);
        trdDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + trdYearDb);
        fthDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + fthYearDb);
        try {
            fstYearConnection = fstDataSource.getConnection();
            sndYearConnection = sndDataSource.getConnection();
            trdYearConnection = trdDataSource.getConnection();
            fthYearConnection = fthDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        repeatedCodesJson.put(FST_YEAR.name(), new LinkedHashMap<>());
        repeatedCorrectedCodesJson.put(FST_YEAR.name(), new LinkedHashMap<>());
        repeatedCodesJson.put(SND_YEAR.name(), new LinkedHashMap<>());
        repeatedCorrectedCodesJson.put(SND_YEAR.name(), new LinkedHashMap<>());
        repeatedCodesJson.put(TRD_YEAR.name(), new LinkedHashMap<>());
        repeatedCorrectedCodesJson.put(TRD_YEAR.name(), new LinkedHashMap<>());
        repeatedCodesJson.put(FTH_YEAR.name(), new LinkedHashMap<>());
        repeatedCorrectedCodesJson.put(FTH_YEAR.name(), new LinkedHashMap<>());
    }

    public static Connection getConnection(StudyYear1444 studyYear) {
        return switch (studyYear) {
            case FST_YEAR -> fstYearConnection;
            case SND_YEAR -> sndYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> fthYearConnection;
            default -> throw new IllegalStateException("Unexpected value: " + studyYear);
        };
    }

    static void updateCode(Connection connection, String updateCodeQuery, String inputCode, String inputFullName, String inputEmail, String resultCode, String partialMatch) {
//        if (!resultCode.equals("")) {
        try (PreparedStatement updateStatement = connection.prepareStatement(updateCodeQuery)) {
            updateStatement.setString(1, resultCode);
            updateStatement.setString(2, partialMatch);
            updateStatement.setString(3, inputCode);
            updateStatement.setString(4, inputFullName);
            updateStatement.setString(5, inputEmail);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        }
    }

    private static void resetCorrectCode() {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);

            var correctedSubjects = listExaminedSubjects(studyYear);
            for (var subject : correctedSubjects) {
                resetCorrectCode(connection, studyYear, subject.getEnglishName());
            }
        }
    }

    static void addColumn() {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);
            var subjects = listNonCorrectCodeSubjects(studyYear);
            for (var subject : subjects) {
                addColumn(connection, subject.getEnglishName(), "right_code", "TEXT");
                addColumn(connection, subject.getEnglishName(), "right_code_2", "TEXT");
                addColumn(connection, subject.getEnglishName(), "partial_match", "TEXT");
            }

        }
    }

    public static void addColumn(Connection connection, String tableName, String columnName, String columnType) {
        try (Statement statement = connection.createStatement()) {
            String sql = "ALTER TABLE " + tableName + " ADD  " + columnName + " " + columnType;
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void resetCorrectCode(Connection connection, StudyYear1444 studyYear, String subject) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("UPDATE " + subject + " SET right_code_2 = '', partial_match = ''");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void deleteNonRepeatedCodes(List<StudyYear1444> studyYears) {
        for (var studyYear : studyYears) {
            for (var subject : listExaminedSubjects(studyYear)) {
                repeatedCodesJson.get(studyYear.name()).get(subject.getEnglishName())
                        .entrySet().removeIf(code -> code.getValue().size() == 1);

                repeatedCorrectedCodesJson.get(studyYear.name()).get(subject.getEnglishName())
                        .entrySet().removeIf(code -> code.getValue().size() == 1);
            }
        }
    }
}
