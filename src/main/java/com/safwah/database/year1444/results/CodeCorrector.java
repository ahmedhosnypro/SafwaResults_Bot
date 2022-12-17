package com.safwah.database.year1444.results;

import com.safwah.Main;
import com.safwah.bot.code.CodeFinder;
import com.safwah.study.year.StudyYear1444;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Pattern;

import static com.safwah.study.year.StudyYear1444.*;


public class CodeCorrector {
    private static final Pattern INVALID_CODE_PATTERN = Pattern.compile("^([e-zE-Z])");
    static int codeLength = 7;
    static String fstCodePrefix = "AD";
    static String sndCodePrefix = "AC";
    static String trdCodePrefix = "AB";
    static String fthCodePrefix = "AA";

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
    }

    public static void main(String[] args) {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);

        for (var studyYear : studyYears) {
            Connection connection = switch (studyYear) {
                case FST_YEAR -> fstYearConnection;
                case SND_YEAR -> sndYearConnection;
                case TRD_YEAR -> trdYearConnection;
                case FTH_YEAR -> fthYearConnection;
                default -> throw new IllegalStateException("Unexpected value: " + studyYear);
            };

            var subjects = listNonCorrectCodeSubjects(studyYear);
            for (var subject : subjects) {
                correctCode(connection, studyYear, subject.getEnglishName());
            }
        }
    }

    private static void correctCode(Connection connection, StudyYear1444 studyYear, String subject) {
        String codeListQuery = String.format("""
                SELECT code, fullName, email
                FROM %s
                """, subject);

        try (Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(codeListQuery);
            while (resultSet.next()) {
                String inputCode = resultSet.getString("code");
                String resultCode = formatCode(inputCode);

                if (!isValidCode(resultCode)) {
                    String fullName = resultSet.getString("fullName").trim().replaceAll("\\s+", "");
                    String email = resultSet.getString("email").trim();
                    resultCode = searchCode(email, fullName, studyYear);
                }


                try (Statement updateStatement = connection.createStatement()) {
                    String updateQuery = String.format("""
                            UPDATE %s
                            SET right_code = '%s'
                            WHERE code = '%s'
                            """, subject, resultCode, inputCode);
                    updateStatement.executeUpdate(updateQuery);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String searchCode(String email, String fullName, StudyYear1444 year) {
        String foundCode = CodeFinder.getCode(fullName, year);
        if (foundCode == null || foundCode.equals("")) {
            foundCode = CodeFinder.getCode(email, year);
        }
        if (foundCode == null || foundCode.equals("")) {
            foundCode = CodeFinder.getCode(fullName);
        }
        if (foundCode == null || foundCode.equals("")) {
            foundCode = CodeFinder.getCode(email);
        }
        if (foundCode == null) {
            foundCode = "";
        }
        return foundCode.toUpperCase();
    }

    public static boolean isValidCode(String code) {
        // if code has valid length
        // and code starts with valid prefix
        // remaining characters are digits
        // and don't have invalid characters
        // then code is valid
        code = formatCode(code);
        return code.length() == codeLength &&
                (code.startsWith(fstCodePrefix) ||
                        code.startsWith(sndCodePrefix) ||
                        code.startsWith(trdCodePrefix) ||
                        code.startsWith(fthCodePrefix)) &&
                code.substring(2).matches("\\d+") &&
                !INVALID_CODE_PATTERN.matcher(code).find();
    }

    public static String formatCode(String inputCode) {
        return inputCode.trim().toUpperCase()
                .replaceAll("٠", "0")
                .replaceAll("١", "1")
                .replaceAll("٢", "2")
                .replaceAll("٣", "3")
                .replaceAll("٤", "4")
                .replaceAll("٥", "5")
                .replaceAll("٦", "6")
                .replaceAll("٧", "7")
                .replaceAll("٨", "8")
                .replaceAll("٩", "9")
                .replaceAll("\\s+", "")
                .replaceAll("o", "0")
                .replaceAll("O", "0");
    }
}
