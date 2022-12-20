package com.safwah.database.code;

import com.safwah.Main;
import com.safwah.Student;
import com.safwah.logger.Logger;
import com.safwah.study.year.*;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CodeDataBase {
    private CodeDataBase() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource fstDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource sndDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource trdDataSource = new SQLiteDataSource();

    private static final SQLiteDataSource _4thDataSource = new SQLiteDataSource();
    public static final Connection fstYearConnection;
    public static final Connection sndYearConnection;
    public static final Connection trdYearConnection;
    public static final Connection _4thYearConnection;

    static {
        fstDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.FST_YEAR.getCodeDataBaseName());
        sndDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.SND_YEAR.getCodeDataBaseName());
        trdDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.TRD_YEAR.getCodeDataBaseName());
        _4thDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.FTH_YEAR.getCodeDataBaseName());
        try {
            fstYearConnection = fstDataSource.getConnection();
            sndYearConnection = sndDataSource.getConnection();
            trdYearConnection = trdDataSource.getConnection();
            _4thYearConnection = _4thDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCode(String nameOrEmail) {
        if (getCode(StudyYear1444.FTH_YEAR, nameOrEmail) != null) {
            return getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
        } else if (getCode(StudyYear1444.TRD_YEAR, nameOrEmail) != null) {
            return getCode(StudyYear1444.TRD_YEAR, nameOrEmail);
        } else if (getCode(StudyYear1444.SND_YEAR, nameOrEmail) != null) {
            return getCode(StudyYear1444.SND_YEAR, nameOrEmail);
        } else {
            return getCode(StudyYear1444.FST_YEAR, nameOrEmail);
        }
    }


    public static String getCode(String nameOrEmail, StudyYear1444 year) {
        return getCode(year, nameOrEmail);
    }


    public static String getHigherCode(String nameOrEmail, StudyYear1444 year) {
        return switch (year) {
            case FST_YEAR -> {
                String code = getCode(StudyYear1444.SND_YEAR, nameOrEmail);
                if (code == null) {
                    code = getCode(StudyYear1444.TRD_YEAR, nameOrEmail);
                }
                if (code == null) {
                    code = getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
                }
                yield code;
            }
            case SND_YEAR -> {
                String code = getCode(StudyYear1444.TRD_YEAR, nameOrEmail);
                if (code == null) {
                    code = getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
                }
                yield code;
            }
            case TRD_YEAR, FTH_YEAR -> getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
            case ERROR -> null;
        };
    }

    public static Student getStudent(String resultCode, StudyYear1444 year) {
        return getStudent(year, resultCode);
    }

    private static String getCode(StudyYear1444 studyYear, String nameOrEmail) {
        Connection con = switch (studyYear) {
            case SND_YEAR -> sndYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> _4thYearConnection;
            default -> fstYearConnection;
        };
        return getCode(nameOrEmail, con);
    }

    private static Student getStudent(StudyYear1444 studyYear, String resultCode) {
        Connection con = switch (studyYear) {
            case SND_YEAR -> sndYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> _4thYearConnection;
            default -> fstYearConnection;
        };
        return getStudentByCode(resultCode, con);
    }

    private static Student getStudentByCode(String resultCode, Connection con) {
        String getResultQuery = String.format("""
                SELECT *
                FROM users
                where upper(code) = '%s'
                   """, resultCode.toUpperCase());
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String code = resultSet.getString("code");
                return new Student(email == null ? "" : email,
                        name == null ? "" : name,
                        code == null ? "" : code);
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    private static String getCode(String nameOrEmail, Connection con) {
        String getResultQuery = String.format("""
                        SELECT code
                        FROM users
                        where trim(
                              replace(
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
                                                      '\\n', ''),
                                              '\\t', ''),
                                      ' ', '')) like '%s'
                          OR lower(email) LIKE '%s'
                           """, nameOrEmail.replaceAll("\\s+", "")
                        .replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه")
                        .replaceAll("'", "''"),
                nameOrEmail.toLowerCase()
                        .replaceAll("'", "''"));
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }


    public static boolean isExists(String nameOrEmail) {
        if (isExists(nameOrEmail, _4thYearConnection)) {
            return true;
        } else if (isExists(nameOrEmail, trdYearConnection)) {
            return true;
        } else if (isExists(nameOrEmail, sndYearConnection)) {
            return true;
        } else {
            return isExists(nameOrEmail, fstYearConnection);
        }
    }

    private static boolean isExists(String nameOrEmail, Connection con) {
        String isExistQuery = String.format("""
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
                                                                                      replace(
                                                                                              replace(name, 'إ', 'ا'),
                                                                                              'أ', 'ا'),
                                                                                      'ى', 'ي'),
                                                                              'ة', 'ه'),
                                                                      'ؤ', 'و'),
                                                              'آ', 'ا'),
                                                      '\\n', ''),
                                              '\\t', ''),
                                      ' ', '')) = '%s'
                          OR lower(email) = '%s'
                           """, nameOrEmail.replaceAll("\\s+", "")
                        .replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه")
                        .replaceAll("'", "''"),
                nameOrEmail.toLowerCase()
                        .replaceAll("'", "''"));
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
