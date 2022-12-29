package com.safwah.database.result.institute;

import com.safwah.Student;
import com.safwah.bot.code.bot.institute.InstituteCodeFinderBot;
import com.safwah.bot.code.corrector.CodeUtilities;
import com.safwah.bot.code.corrector.DataBaseEditor;
import com.safwah.study.year.YearsSubject;
import com.safwah.study.year.institute.InstituteStudyYear1444;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.safwah.bot.code.corrector.DataBaseEditor.getConnection;
import static com.safwah.bot.result.Results.intScore;

public class InstituteAllResultsExporter {

    public static final String ALL_RESULTS = "allResults";

    public static void main(String[] args) {
        exportAllResults();
//        FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR
    }

    private static void exportAllResults() {
        addColumns(InstituteStudyYear1444.FST_YEAR);
        exportResults(getConnection(InstituteStudyYear1444.FST_YEAR), InstituteStudyYear1444.FST_YEAR);
    }

    private static void exportResults(Connection connection, InstituteStudyYear1444 studyYear) {
        var subjects = InstituteStudyYear1444.listExaminedSubjects(studyYear);
        for (var subject : subjects) {
//            Main.EXECUTOR.submit(() -> {
                var studentsResults = getAllResults(connection, subject, studyYear);
                addResultsToTable(studentsResults, subject, connection);
//            });
        }
    }

    private static void addResultsToTable(List<Map<String, String>> studentsResults, YearsSubject subject, Connection connection) {
        for (var studentResult : studentsResults) {
//            Main.EXECUTOR.submit(() -> {
            String code = studentResult.get("code");
            try (Statement statement = connection.createStatement()) {
                String isCodeExistQuery = "SELECT * FROM " + ALL_RESULTS + " WHERE upper(code) = '" + code + "'";
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
//            });
        }
    }

    private static List<Student> students = new ArrayList<>();

    private static List<Map<String, String>> getAllResults(Connection connection, YearsSubject subject, InstituteStudyYear1444 studyYear) {
        List<Map<String, String>> allResults = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String QUERY = "SELECT * FROM " + subject.getEnglishName();
            var resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                Map<String, String> results = new HashMap<>();
                String code = resultSet.getString("right_code_2");
                String formattedCode = CodeUtilities.formatCode(code);
//                if (CodeUtilities.isValidInstituteCode(code)) {
                var student = InstituteCodeFinderBot.getStudent(formattedCode, studyYear);
                if (student == null) {
                    if (students.stream().anyMatch(s -> s.getCode().equals(formattedCode))) {
                        student = students.stream().filter(s -> s.getCode().equals(formattedCode)).findFirst().get();
                    } else {
                        student = new Student(resultSet.getString("email"), resultSet.getString("fullName"), formattedCode);
                        students.add(student);
                    }
                }

                var studentName = student.getName();
                results.put("code", formattedCode);
                results.put("name", studentName.replaceAll("'", "''"));
                results.put("email", resultSet.getString("email").replaceAll("'", "''"));
                results.put("result", String.valueOf(intScore(resultSet.getString("totalScore")) * 2));
                allResults.add(results);
//                    }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allResults;
    }

    static void addColumns(InstituteStudyYear1444 instituteStudyYear1444) {
        DataBaseEditor.addColumn(getConnection(instituteStudyYear1444), ALL_RESULTS, "email", "TEXT");
        DataBaseEditor.addColumn(getConnection(instituteStudyYear1444), ALL_RESULTS, "name", "TEXT");
        DataBaseEditor.addColumn(getConnection(instituteStudyYear1444), ALL_RESULTS, "code", "TEXT");
        for (var subject : com.safwah.study.year.institute.InstituteStudyYear1444.listExaminedSubjects(instituteStudyYear1444)) {
            DataBaseEditor.addColumn(getConnection(instituteStudyYear1444), ALL_RESULTS, subject.getArabicName().replaceAll(" ", "_"), "INTEGER");
        }
    }
}
