package com.safwah.database.result._1444;

import com.safwah.Main;
import com.safwah.bot.code.bot.CodeFinder;
import com.safwah.bot.code.corrector.CodeUtilities;
import com.safwah.bot.code.corrector.DataBaseEditor;
import com.safwah.study.year.StudyYear1444;
import com.safwah.study.year.YearsSubject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.safwah.bot.code.corrector.DataBaseEditor.getConnection;
import static com.safwah.bot.result.Results.intScore;
import static com.safwah.study.year.StudyYear1444.*;

public class AllResultsExporter {

    public static final String ALL_RESULTS = "allResults";

    public static void main(String[] args) {
        exportAllResults(SND_YEAR);
//        FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR
    }

    private static void exportAllResults(StudyYear1444 studyYear1444) {
        addColumns(studyYear1444);
        Connection connection = getConnection(studyYear1444);
        exportResults(connection, studyYear1444);
    }

    private static void exportResults(Connection connection, StudyYear1444 studyYear) {
        var subjects = listExaminedSubjects(studyYear);
        for (var subject : subjects) {
            Main.EXECUTOR.submit(() -> {
                var studentsResults = getAllResults(connection, subject, studyYear);
                addResultsToTable(studentsResults, subject, connection);
            });
        }
    }

    private static void addResultsToTable(List<Map<String, String>> studentsResults, YearsSubject subject, Connection connection) {
        for (var studentResult : studentsResults) {
            Main.EXECUTOR.submit(() -> {
                String code = studentResult.get("code");
                try (Statement statement = connection.createStatement()) {
                    String isCodeExistQuery = "SELECT * FROM " + ALL_RESULTS + " WHERE code = '" + code + "'";
                    var resultSet = statement.executeQuery(isCodeExistQuery);
                    if (resultSet.next()) {
                        String updateQuery = "UPDATE " + ALL_RESULTS + " SET " + subject.getArabicName().replaceAll(" ", "_") + " = " + Integer.parseInt(studentResult.get("result")) + " WHERE code = '" + code + "'";
                        statement.execute(updateQuery);
                    } else {
                        String insertQuery = "INSERT INTO " + ALL_RESULTS + " (code, name, email, " + subject.getArabicName().replaceAll(" ", "_") + ") " +
                                "VALUES ('" + code + "', '" +
                                studentResult.get("name") + "', '" +
                                studentResult.get("email") + "', " +
                                Integer.parseInt(studentResult.get("result")) + ")";
                        statement.execute(insertQuery);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static List<Map<String, String>> getAllResults(Connection connection, YearsSubject subject, StudyYear1444 studyYear) {
        List<Map<String, String>> allResults = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String QUERY = "SELECT * FROM " + subject.getEnglishName();
            var resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                Map<String, String> results = new HashMap<>();
                String code = resultSet.getString("right_code_2");
                if (CodeUtilities.isValidCode(code)) {
                    var student = CodeFinder.getStudent(code, studyYear);
                    if (student != null) {
                        var studentName = student.getName();
                        results.put("code", code);
                        results.put("name", studentName.replaceAll("'", "''"));
                        results.put("email", resultSet.getString("email").replaceAll("'", "''"));
                        results.put("result", String.valueOf(intScore(resultSet.getString("totalScore")) * 2));
                        allResults.add(results);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allResults;
    }

    static void addColumns(StudyYear1444 studyYear1444) {
//        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        List<StudyYear1444> studyYears = List.of(studyYear1444);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);
            DataBaseEditor.addColumn(connection, ALL_RESULTS, "email", "TEXT");
            DataBaseEditor.addColumn(connection, ALL_RESULTS, "name", "TEXT");
            DataBaseEditor.addColumn(connection, ALL_RESULTS, "code", "TEXT");
            var subjects = listExaminedSubjects(studyYear);
            for (var subject : subjects) {
                DataBaseEditor.addColumn(connection, ALL_RESULTS, subject.getArabicName().replaceAll(" ", "_"), "INTEGER");
            }
        }
    }
}
