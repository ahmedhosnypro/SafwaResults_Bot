package com.safwah.database.result._1443;

import com.safwah.study.year.university.StudyYear;
import com.safwah.Main;
import com.safwah.logger.Logger;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdYearDataBase {
    private IdYearDataBase() {
    }

    private static final String BB_NAME = "chatIdYears.db";

    private static final SQLiteDataSource dataSource = new SQLiteDataSource();
    private static final Connection ID_CONNECTION;

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
        dataSource.setUrl("jdbc:sqlite:" + Main.getResourcePath() + BB_NAME);
        try {
            ID_CONNECTION = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean setIdYear(long chatId, String inputStudyYear) {
        try {
            StudyYear studyYear = StudyYear.getByArabicNotation(inputStudyYear);
            if (addNewId(chatId, studyYear)) {
                return updateId(chatId, studyYear);
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    static boolean addNewId(long chatId, StudyYear studyYear) {
        try (PreparedStatement statement = IdYearDataBase.ID_CONNECTION.prepareStatement(ADD_QUERY)) {
            statement.setString(1, String.valueOf(chatId));
            statement.setString(2, studyYear.toString());
            return statement.execute();
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            return true;
        }
    }

    static boolean updateId(long chatId, StudyYear studyYear) {
        try (PreparedStatement statement = IdYearDataBase.ID_CONNECTION.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, studyYear.toString());
            statement.setString(2, String.valueOf(chatId));
            return !statement.execute();
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            return true;
        }
    }

    public static StudyYear getStudyYear(long chatId) {
        try (PreparedStatement statement = ID_CONNECTION.prepareStatement(SELECT_QUERY)) {
            statement.setString(1, String.valueOf(chatId));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return StudyYear.valueOf(resultSet.getString("year"));
            } else {
                return StudyYear.ERROR;
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            return StudyYear.ERROR;
        }
    }

    public static void updateSrcLocation() {
        dataSource.setUrl("jdbc:sqlite:" + Main.getResourcePath() + BB_NAME);
        Logger.log(Main.getResourcePath() + BB_NAME);
    }

    public void closeConnection() {
        try {
            ID_CONNECTION.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
