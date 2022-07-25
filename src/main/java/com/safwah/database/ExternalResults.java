package com.safwah.database;

import com.safwah.logger.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExternalResults {

    private static final String AQIDA_EXAM_ID = "5";
    private static final String TAGWID_EXAM_ID = "6";
    static final String GET_ID_QUERY = """
            SELECT * FROM users
            WHERE  name = ? OR email = ?
            """;

    static final String IS_CORRECT_ANSWER_QUERY = """
            SELECT is_correct FROM answers
            WHERE id = ?
            """;

    static final String GET_RESULTS_QUERY = """
            SELECT * FROM students_responses
            WHERE student_id = ? AND exam_id = ?
            """;
    static final String DB_URL = "jdbc:mariadb://localhost:3306/safwauni_app";
    static final String USER = "root";
    static final String PASS = "";

    private static List<Integer> getIds(String nameOrEmail) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String GET_ID_QUERY = String.format("""
                        SELECT *
                        FROM users
                        where trim(
                                      replace(
                                              replace(
                                                      replace(
                                                              replace(
                                                                      replace(
                                                                              replace(
                                                                                      replace(
                                                                                              replace(name, 'إ', 'ا'),
                                                                                              'أ', 'ا'),
                                                                                      'ى', 'ي'),
                                                                              'ة', 'ه'),
                                                                      'ؤ', 'و'),
                                                              'آ', 'ا'),
                                                      '  ', ' '),
                                              '   ', ' ')) like '%%%s%%'
                           OR lower(email) LIKE '%%%s%%'
                           """,
                nameOrEmail.replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه"),
                nameOrEmail.toLowerCase());

        List<Integer> ids = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(GET_ID_QUERY);) {
            stmt.setString(1, nameOrEmail);
            stmt.setString(2, nameOrEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return ids;
    }

    private static List<String> getAnswersIds(int studentId, int examId) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> answersIds = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(GET_RESULTS_QUERY);) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, examId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                answersIds.add(rs.getString("answer_id"));
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return answersIds;
    }

    private static String calcScore(List<String> answersIds) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        int score = 0;
        int fullScore = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            for (String answerId : answersIds) {
                fullScore++;
                PreparedStatement stmt = conn.prepareStatement(IS_CORRECT_ANSWER_QUERY);
                stmt.setString(1, answerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    var isCorrect = rs.getInt("is_correct");
                    if (isCorrect == 1) {
                        score++;
                    }
                }
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return "" + score + "/" + fullScore;
    }

    public static String getResult(String nameOrEmail, int examId) {
        String result = "";
        var ids = getIds(nameOrEmail);
        if (ids.isEmpty()) {
            return null;
        }

        List<String> answersIds = new ArrayList<>();
        for (var id : ids) {
            answersIds = getAnswersIds(id, examId);
            if (!answersIds.isEmpty()) {
                break;
            }
        }

        result += calcScore(answersIds) + "\n";
        return result;
    }
}
