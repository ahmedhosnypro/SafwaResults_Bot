package com.safwah.database.code.institute;

import com.safwah.Main;
import com.safwah.Student;
import com.safwah.logger.Logger;
import com.safwah.study.year.institute.InstituteStudyYear1444;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static com.safwah.bot.code.corrector.CodeFinder.getMatchCount;

public class InstituteCodeDataBase {
    private InstituteCodeDataBase() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource fstDataSource = new SQLiteDataSource();
    //    private static final SQLiteDataSource sndDataSource = new SQLiteDataSource();
//    private static final SQLiteDataSource trdDataSource = new SQLiteDataSource();
//    private static final SQLiteDataSource _4thDataSource = new SQLiteDataSource();
    public static final Connection fstYearConnection;
//    public static final Connection sndYearConnection;
//    public static final Connection trdYearConnection;
//    public static final Connection _4thYearConnection;


    static {
        fstDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + InstituteStudyYear1444.FST_YEAR.getCodeDataBaseName());
//        sndDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.SND_YEAR.getCodeDataBaseName());
//        trdDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.TRD_YEAR.getCodeDataBaseName());
//        _4thDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.FTH_YEAR.getCodeDataBaseName());
        try {
            fstYearConnection = fstDataSource.getConnection();
//            sndYearConnection = sndDataSource.getConnection();
//            trdYearConnection = trdDataSource.getConnection();
//            _4thYearConnection = _4thDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[] getCode(String nameOrEmail) {
//        var result = getCode(InstituteStudyYear1444.FTH_YEAR, nameOrEmail);
//
//        if (result == null) {
//            result = getCode(InstituteStudyYear1444.TRD_YEAR, nameOrEmail);
//            if (result == null) {
//                result = getCode(InstituteStudyYear1444.SND_YEAR, nameOrEmail);
//                if (result == null) {
//                    result = getCode(InstituteStudyYear1444.FST_YEAR, nameOrEmail);
//                }
//            }
//        }
        //todo return result
        return getCode(InstituteStudyYear1444.FST_YEAR, nameOrEmail);
    }

    @Nullable
    public static String[] getCodeByTryingMatchingNames(String nameOrEmail, boolean isRepeating) {
//        var result = getCodeByTryingMatchingNames(nameOrEmail, InstituteStudyYear1444.FTH_YEAR, isRepeating);
//        if (result == null) {
//            result = getCodeByTryingMatchingNames(nameOrEmail, InstituteStudyYear1444.TRD_YEAR, isRepeating);
//            if (result == null) {
//                result = getCodeByTryingMatchingNames(nameOrEmail, InstituteStudyYear1444.SND_YEAR, isRepeating);
//                if (result == null) {
//                    result = getCodeByTryingMatchingNames(nameOrEmail, InstituteStudyYear1444.FST_YEAR, isRepeating);
//                }
//            }
//        }
        //todo edit return to return result
        return getCodeByTryingMatchingNames(nameOrEmail, InstituteStudyYear1444.FST_YEAR, isRepeating);
    }


    public static String[] getCode(String nameOrEmail, InstituteStudyYear1444 year) {
        return getCode(year, nameOrEmail);
    }

    public static String[] getCodeByTryingMatchingNames(String fullName, InstituteStudyYear1444 year, boolean isRepeating) {
        return getCodeByTryingMatchingNames(year, fullName, isRepeating);
    }

    public static String[] getHigherCode(String nameOrEmail, InstituteStudyYear1444 year) {
//        return switch (year) {
//            case FST_YEAR -> {
//                String[] result = getCode(InstituteStudyYear1444.SND_YEAR, nameOrEmail);
//
//                if (result == null) {
//                    result = getCode(InstituteStudyYear1444.TRD_YEAR, nameOrEmail);
//                }
//                if (result == null) {
//                    result = getCode(InstituteStudyYear1444.FTH_YEAR, nameOrEmail);
//                }
//                yield result;
//            }
//            case SND_YEAR -> {
//                String[] result = getCode(InstituteStudyYear1444.TRD_YEAR, nameOrEmail);
//                if (result == null) {
//                    result = getCode(InstituteStudyYear1444.FTH_YEAR, nameOrEmail);
//                }
//                yield result;
//            }
//            case TRD_YEAR, FTH_YEAR -> getCode(InstituteStudyYear1444.FTH_YEAR, nameOrEmail);
//            case ERROR -> null;
//        };
        return null;
    }

    public static Student getStudent(String resultCode, InstituteStudyYear1444 year) {
        return getStudent(year, resultCode);
    }

    private static String[] getCode(InstituteStudyYear1444 studyYear, String nameOrEmail) {
        Connection con = switch (studyYear) {
//            case SND_YEAR -> sndYearConnection;
//            case TRD_YEAR -> trdYearConnection;
//            case FTH_YEAR -> _4thYearConnection;
            default -> fstYearConnection;
        };
        return getCode(nameOrEmail, con);
    }

    private static String[] getCodeByTryingMatchingNames(InstituteStudyYear1444 studyYear, String fullName, boolean isRepeating) {
        Connection con = switch (studyYear) {
//            case SND_YEAR -> sndYearConnection;
//            case TRD_YEAR -> trdYearConnection;
//            case FTH_YEAR -> _4thYearConnection;
            default -> fstYearConnection;
        };
        return getCodeByTryingMatchingNames(fullName, con, isRepeating);
    }

    private static Student getStudent(InstituteStudyYear1444 studyYear, String resultCode) {
        Connection con = switch (studyYear) {
//            case SND_YEAR -> sndYearConnection;
//            case TRD_YEAR -> trdYearConnection;
//            case FTH_YEAR -> _4thYearConnection;
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
                return new Student(email == null ? "" : email.toLowerCase(),
                        name == null ? "" : name,
                        code == null ? "" : code);
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    private static String[] getCodeByTryingMatchingNames(String resultFullName, Connection con, boolean isRepeating) {
        String getResultQuery = """
                SELECT name, code
                FROM users
                """;
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);

            var result = new String[2];
            int matchCount = 0;
            while (resultSet.next()) {
                String studentFullName = resultSet.getString("name");
                String code = resultSet.getString("code");

                //todo
                {
//                    //debugging
//                    if (!studentFullName.equals("حفصة المختار عبد الرحمان الرامي")) {
//                        continue;
//                    }
//                    System.out.println("studentFullName = " + studentFullName + " code = " + code);
//                    if (!code.equals("AD20089")) {
//                        continue;
//                    }
                }
                studentFullName = formatName(studentFullName);
                resultFullName = formatName(resultFullName);

                var studentFullNameParts = studentFullName.split(" ");
                var resultFullNameParts = resultFullName.split(" ");
                boolean isAllMatched = true;
                if (studentFullNameParts.length == resultFullNameParts.length) {
                    for (int i = 0; i < studentFullNameParts.length; i++) {
                        if (!studentFullNameParts[i].equals(resultFullNameParts[i])) {
                            isAllMatched = false;
                            break;
                        }
                    }
                } else {
                    isAllMatched = false;
                }

                if (isAllMatched) {
                    result[0] = studentFullName;
                    result[1] = code;
                    return result;
                }

                matchCount += getPartialMatchCount(resultFullName, result, studentFullName, code);
                if (!isRepeating && matchCount == 1) {
                    return result;
                }
            }
            if (matchCount != 0) {
                return result;
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    private static int getPartialMatchCount(String resultFullName, String[] result, String studentFullName, String code) {
        LinkedHashSet<String> studentFullNameSet = new LinkedHashSet<>(Arrays.asList(studentFullName.split(" ")));
        LinkedHashSet<String> resultFullNameSet = new LinkedHashSet<>(Arrays.asList(resultFullName.split(" ")));

        if (!(studentFullNameSet.size() < 2 || resultFullNameSet.size() < 2 ||
                !studentFullNameSet.iterator().next().equals(resultFullNameSet.iterator().next()))) {
            //try to find the same name
            int partialMatchCount = 0;
            partialMatchCount = getMatchCount(studentFullNameSet, resultFullNameSet, partialMatchCount);

            if (partialMatchCount > 2) {
                result[0] = studentFullName;
                result[1] = code.toUpperCase();
                return 1;
            }
        }
        return 0;
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
//            if (resultSet.next()) {
//                return new String[]{resultSet.getString("name"), resultSet.getString("code").toUpperCase()};
//            }

            var result = new String[2];

            int matchCount = 0;
            while (resultSet.next()) {
                if (matchCount != 0) {
                    matchCount++;
                    break;
                }
                result[0] = resultSet.getString("name");
                result[1] = resultSet.getString("code").toUpperCase();
                matchCount++;
            }
            if (matchCount == 1) {
                return result;
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
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
