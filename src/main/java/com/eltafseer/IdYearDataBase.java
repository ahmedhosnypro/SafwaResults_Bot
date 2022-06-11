package com.eltafseer;

import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdYearDataBase {
    private static final String BB_URL = "chatIdYears.db";

    private static final SQLiteDataSource dataSource = new SQLiteDataSource();

    private static final String UPDATE_QUERY = """
            UPDATE chatIdYears
            SET year = ?
            where chatId = ?
            """;

    private static final String ADD_QUERY = """
            INSERT INTO chatIdYears
            VALUES (?, ?)
            """;

    private static final String SELECT_QUERY = """
            SELECT year
            FROM chatIdYears
            WHERE chatId = ?
            """;

    static {
        var jarPath = ResultsDataBase.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        String jarParentPath = new File(jarPath).getAbsolutePath();
        if (jarParentPath.endsWith("\\SafwaResults.exe")) {
            jarParentPath = jarParentPath.replace("\\SafwaResults.exe", "");
        }
        dataSource.setUrl("jdbc:sqlite:" + jarParentPath + "\\" + BB_URL);
    }

    static boolean setIdYear(long chatId, String inputStudyYear) {
        try (Connection connection = dataSource.getConnection()) {
            StudyYear studyYear = StudyYear.getByArabicNotation(inputStudyYear);
            if (addNewId(chatId, studyYear, connection)) {
                return updateId(chatId, studyYear, connection);
            } else {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean addNewId(long chatId, StudyYear studyYear, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setString(1, String.valueOf(chatId));
            statement.setString(2, studyYear.toString());
            return statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    static boolean updateId(long chatId, StudyYear studyYear, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, studyYear.toString());
            statement.setString(2, String.valueOf(chatId));
            return !statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    static StudyYear getStudyYear(long chatId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            statement.setString(1, String.valueOf(chatId));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return StudyYear.valueOf(resultSet.getString("year"));
            } else {
                return StudyYear.ERROR;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return StudyYear.ERROR;
        }
    }
}
