package com.safwah.database.code;

import com.safwah.Main;
import com.safwah.Student;
import com.safwah.logger.Logger;
import com.safwah.study.year.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static com.safwah.bot.result._1444.CodeCorrector.getMatchCount;

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

    public static String[] getCode(String nameOrEmail) {
        var result = getCode(StudyYear1444.FTH_YEAR, nameOrEmail);

        if (result == null) {
            result = getCode(StudyYear1444.TRD_YEAR, nameOrEmail);
            if (result == null) {
                result = getCode(StudyYear1444.SND_YEAR, nameOrEmail);
                if (result != null) {
                    result = getCode(StudyYear1444.FST_YEAR, nameOrEmail);
                }
            }
        }
        return result;
    }

    @Nullable
    public static String[] getCodeByTryingMatchingNames(String nameOrEmail) {
        var result = getCodeByTryingMatchingNames(nameOrEmail, StudyYear1444.FTH_YEAR);
        if (result == null) {
            result = getCodeByTryingMatchingNames(nameOrEmail, StudyYear1444.TRD_YEAR);
            if (result == null) {
                result = getCodeByTryingMatchingNames(nameOrEmail, StudyYear1444.SND_YEAR);
                if (result != null) {
                    result = getCodeByTryingMatchingNames(nameOrEmail, StudyYear1444.FST_YEAR);
                }
            }
        }
        return result;
    }


    public static String[] getCode(String nameOrEmail, StudyYear1444 year) {
        return getCode(year, nameOrEmail);
    }

    public static String[] getCodeByTryingMatchingNames(String fullName, StudyYear1444 year) {
        return getCodeByTryingMatchingNames(year, fullName);
    }

    public static String[] getHigherCode(String nameOrEmail, StudyYear1444 year) {
        return switch (year) {
            case FST_YEAR -> {
                String[] result = getCode(StudyYear1444.SND_YEAR, nameOrEmail);

                if (result == null) {
                    result = getCode(StudyYear1444.TRD_YEAR, nameOrEmail);
                }
                if (result == null) {
                    result = getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
                }
                yield result;
            }
            case SND_YEAR -> {
                String[] result = getCode(StudyYear1444.TRD_YEAR, nameOrEmail);
                if (result == null) {
                    result = getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
                }
                yield result;
            }
            case TRD_YEAR, FTH_YEAR -> getCode(StudyYear1444.FTH_YEAR, nameOrEmail);
            case ERROR -> null;
        };
    }

    public static Student getStudent(String resultCode, StudyYear1444 year) {
        return getStudent(year, resultCode);
    }

    private static String[] getCode(StudyYear1444 studyYear, String nameOrEmail) {
        Connection con = switch (studyYear) {
            case SND_YEAR -> sndYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> _4thYearConnection;
            default -> fstYearConnection;
        };
        return getCode(nameOrEmail, con);
    }

    private static String[] getCodeByTryingMatchingNames(StudyYear1444 studyYear, String fullName) {
        Connection con = switch (studyYear) {
            case SND_YEAR -> sndYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> _4thYearConnection;
            default -> fstYearConnection;
        };
        return getCodeByTryingMatchingNames(fullName, con);
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

    private static String[] getCodeByTryingMatchingNames(String resultFullName, Connection con) {
        String getResultQuery = """
                SELECT name, code
                FROM users
                   """;
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            while (resultSet.next()) {
                String studentFullName = resultSet.getString("name");

                //todo
                //debugging
//                if (!studentFullName.equals("ألان سوارمان ابن ألادين ابن عبد الرحمن")) {
//                    continue;
//                }

                String code = resultSet.getString("code");


                studentFullName = formatName(studentFullName);
                resultFullName = formatName(resultFullName);

                LinkedHashSet<String> studentFullNameSet = new LinkedHashSet<>(Arrays.asList(studentFullName.split(" ")));
                LinkedHashSet<String> resultFullNameSet = new LinkedHashSet<>(Arrays.asList(resultFullName.split(" ")));

                if (!(studentFullNameSet.size() < 2 || resultFullNameSet.size() < 2 ||
                        !studentFullNameSet.iterator().next().equals(resultFullNameSet.iterator().next()))) {
                    //try to find the same name
                    int matchCount = 0;
                    matchCount = getMatchCount(studentFullNameSet, resultFullNameSet, matchCount);

                    if (matchCount > 2) {
                        return new String[]{studentFullName, code.toUpperCase()};
                    }
                }
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    @NotNull
    private static String formatName(String studentFullName) {
        studentFullName = studentFullName.replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه")
                .replaceAll("عبد ال", "عبدال")
                .replaceAll("[\u064B-\u0652]", "")
                .replaceAll("[-،._]", " ");
        return studentFullName;
    }

    private static String[] getCode(String nameOrEmail, Connection con) {
        //todo
        String getResultQuery = "SELECT name, code FROM users where " +
                TRIMMING + " = '" + nameOrEmail
                .replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه")
                .replaceAll("[ً-ْ]", "")
                .replaceAll("'", "''")
                .replaceAll("[-،._]", " ")
                .replaceAll("عبد ال", "عبدال")
                .replaceAll("\\s+", "") +
                "' OR lower(email) = '" + nameOrEmail.toLowerCase() + "'";

        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            if (resultSet.next()) {
                return new String[]{resultSet.getString("name"), resultSet.getString("code").toUpperCase()};
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }


    public static boolean isCodeExistsFor(String nameOrEmail) {
        if (isCodeExistsFor(nameOrEmail, _4thYearConnection)) {
            return true;
        } else if (isCodeExistsFor(nameOrEmail, trdYearConnection)) {
            return true;
        } else if (isCodeExistsFor(nameOrEmail, sndYearConnection)) {
            return true;
        } else {
            return isCodeExistsFor(nameOrEmail, fstYearConnection);
        }
    }

    public static boolean isCodeExist(String formattedCode) {
        if (isCodeExist(formattedCode, _4thYearConnection)) {
            return true;
        } else if (isCodeExist(formattedCode, trdYearConnection)) {
            return true;
        } else if (isCodeExist(formattedCode, sndYearConnection)) {
            return true;
        } else {
            return isCodeExist(formattedCode, fstYearConnection);
        }
    }

    public static boolean isCodeExist(String formattedCode, Connection con) {
        //check if the code is in the database
        String checkCodeQuery =
                "SELECT * FROM users  where " + "upper(code) = '" + formattedCode + "'";

        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(checkCodeQuery);
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return false;
    }


    private static boolean isCodeExistsFor(String nameOrEmail, Connection con) {
        String name = nameOrEmail.replaceAll("\\s+", "")
                .replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه")
                .replaceAll("'", "''")
                .replaceAll("[ً-ْ]", "")
                .replaceAll("[-،._]", " ")
                .replaceAll("عبد ال", "عبدال");
        String email = nameOrEmail.toLowerCase()
                .replaceAll("'", "''");

        String isExistQuery =
                "SELECT * FROM users  where " + TRIMMING + " = '" + name +
                        "' OR lower(email) = '" + email + "'";
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

    private static final String TRIMMING;

    static {
        TRIMMING = """
                trim(
                              replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(
                                                                                                              replace(
                                                                                                                      replace(
                                                                                                                              replace(
                                                                                                                                      replace(
                                                                                                                                              replace(
                                                                                                                                                      replace(
                                                                                                                                                              replace(
                                                                                                                                                                      replace(
                                                                                                                                                                              replace(
                                                                                                                                                                                      replace(
                                                                                                                                                                                              replace(
                                                                                                                                                                                                      replace(
                                                                                                                                                                                                              replace(
                                                                                                                                                                                                                      replace(
                                                                                                                                                                                                                              replace(
                                                                                                                                                                                                                                      replace(
                                                                                                                                                                                                                                              replace(
                                                                                                                                                                                                                                                      replace(name, 'إ', 'ا'),
                                                                                                                                                                                                                                                      'أ',
                                                                                                                                                                                                                                                      'ا'),
                                                                                                                                                                                                                                              'ى',
                                                                                                                                                                                                                                              'ي'),
                                                                                                                                                                                                                                      'ة',
                                                                                                                                                                                                                                      'ه'),
                                                                                                                                                                                                                              'ؤ',
                                                                                                                                                                                                                              'و'),
                                                                                                                                                                                                                      'آ',
                                                                                                                                                                                                                      'ا'),
                                                                                                                                                                                                              '\\n',
                                                                                                                                                                                                              ''),
                                                                                                                                                                                                      '\\t',
                                                                                                                                                                                                      ''),
                                                                                                                                                                                              ' ',
                                                                                                                                                                                              ''),
                                                                                                                                                                                      'ٌّ',
                                                                                                                                                                                      ''),
                                                                                                                                                                              'ُّ',
                                                                                                                                                                              ''),
                                                                                                                                                                      'ًّ',
                                                                                                                                                                      ''),
                                                                                                                                                              'َّ',
                                                                                                                                                              ''),
                                                                                                                                                      'ٌ',
                                                                                                                                                      ''),
                                                                                                                                              'ُ',
                                                                                                                                              ''),
                                                                                                                                      'ً',
                                                                                                                                      ''),
                                                                                                                              'َ', ''),
                                                                                                                      'ِ', ''),
                                                                                                              'ٍ', ''),
                                                                                                      'ّ', ''),
                                                                                              'ْ', ''),
                                                                                      'ِّ', ''),
                                                                              'ٍّ', ''),
                                                                      '،', ''),
                                                              '.', ''),
                                                      '-', ''),
                                              '_', ''),
                                      'ّْ', ''),
                                  'عبد ال', 'عبدال'))
                """;
    }
}
