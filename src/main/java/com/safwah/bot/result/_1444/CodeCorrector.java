package com.safwah.bot.result._1444;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.safwah.Main;
import com.safwah.bot.code.CodeFinder;
import com.safwah.Person;
import com.safwah.Student;
import com.safwah.study.year.StudyYear1444;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Pattern;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.safwah.study.year.StudyYear1444.*;
import static com.safwah.study.year.StudyYear1444.listExaminedSubjects;


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

    static Map<String, Map<String, Map<String, List<Person>>>> repeatedCodesJson = new LinkedHashMap<>();
    static Map<String, Map<String, Map<String, List<Person>>>> repeatedCorrectedCodesJson = new LinkedHashMap<>();
    static List<String> currentSubjectCodes = new ArrayList<>();
    static List<String> currentSubjectCorrectedCodes = new ArrayList<>();

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String CURRENT_SUBJECT_CODES_PATH_NAME = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\code\\repeated_codes.json";
    private static final String CURRENT_SUBJECT_CORRECTED_CODES_PATH_NAME = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\code\\repeated_corrected_codes.json";

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

    public static void main(String[] args) {
        //time now var
        long startTime = System.currentTimeMillis();

//        resetCorrectCode();
        correctCode();
//        addColumn();


        //time now var
        long endTime = System.currentTimeMillis();
        //calculate time in minutes
        long timeElapsed = (endTime - startTime) / 1000;
        System.out.println("Execution time in seconds: " + timeElapsed);
        System.out.println("Execution time in minutes: " + timeElapsed / 60);

    }

    private static void addColumn() {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);
            var subjects = listExaminedSubjects(studyYear);
            for (var subject : subjects) {
                addColumn(connection, studyYear, subject.getEnglishName(), "right_code_2");
            }

        }
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

    private static Connection getConnection(StudyYear1444 studyYear) {
        return switch (studyYear) {
            case FST_YEAR -> fstYearConnection;
            case SND_YEAR -> sndYearConnection;
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> fthYearConnection;
            default -> throw new IllegalStateException("Unexpected value: " + studyYear);
        };
    }

    private static void correctCode() {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);

//        List<StudyYear1444> studyYears = List.of(FST_YEAR);
//        List<StudyYear1444> studyYears = List.of(SND_YEAR);
//        List<StudyYear1444> studyYears = List.of(TRD_YEAR);
//        List<StudyYear1444> studyYears = List.of(FTH_YEAR);

//        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);

            //v1
//            var subjects = listNonCorrectCodeSubjects(studyYear);
//            for (var subject : subjects) {
//                correctCode(connection, studyYear, subject.getEnglishName());
//            }

//            //for code correction v2
            var subjects = listExaminedSubjects(studyYear);
            for (var subject : subjects) {
                currentSubjectCodes = new ArrayList<>();
                currentSubjectCorrectedCodes = new ArrayList<>();
                repeatedCodesJson.get(studyYear.name()).put(subject.getEnglishName(), new LinkedHashMap<>());
                repeatedCorrectedCodesJson.get(studyYear.name()).put(subject.getEnglishName(), new LinkedHashMap<>());
                correctCode(connection, studyYear, subject.getEnglishName());
            }
        }

        // delete non repeated codes from repeatedCodesJson
        for (var studyYear : studyYears) {
            for (var subject : listExaminedSubjects(studyYear)) {
                repeatedCodesJson.get(studyYear.name()).get(subject.getEnglishName())
                        .entrySet().removeIf(code -> code.getValue().size() == 1);

                repeatedCorrectedCodesJson.get(studyYear.name()).get(subject.getEnglishName())
                        .entrySet().removeIf(code -> code.getValue().size() == 1);
            }
        }


        //write to file
        Path currentSubjectCodesPath = Paths.get(CURRENT_SUBJECT_CODES_PATH_NAME);
        Path currentSubjectCorrectedCodesPath = Paths.get(CURRENT_SUBJECT_CORRECTED_CODES_PATH_NAME);
        try (Writer repeatedCodesJsonWriter = Files.newBufferedWriter(currentSubjectCodesPath, StandardCharsets.UTF_8);
             Writer repeatedCorrectedCodesJsonWriter = Files.newBufferedWriter(currentSubjectCorrectedCodesPath, StandardCharsets.UTF_8)) {
            String repeatedCodesJsonString = gson.toJson(repeatedCodesJson);
            String repeatedCorrectedCodesJsonString = gson.toJson(repeatedCorrectedCodesJson);
            repeatedCodesJsonWriter.write(repeatedCodesJsonString);
            repeatedCorrectedCodesJsonWriter.write(repeatedCorrectedCodesJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addColumn(Connection connection, StudyYear1444 studyYear, String englishName, String columnName) {
        try (Statement statement = connection.createStatement()) {
            String sql = "ALTER TABLE " + englishName + " ADD  " + columnName + " TEXT";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void resetCorrectCode(Connection connection, StudyYear1444 studyYear, String subject) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("UPDATE " + subject + " SET right_code_2 = ''");
        } catch (SQLException e) {
            e.printStackTrace();
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


                String fullName = resultSet.getString("fullName").trim();
                String email = resultSet.getString("email").trim();

                addCodeToReport(studyYear, subject, resultCode, fullName, email, currentSubjectCodes, repeatedCodesJson);


                if (!isValidCode(resultCode)) {
                    resultCode = searchCode(email, fullName, studyYear);
                }

                resultCode = validateCode(resultCode, email, fullName, studyYear);

                if (resultCode.equals("")) {
                    resultCode = searchCode(email, fullName, studyYear);
                }

                addCodeToReport(studyYear, subject, resultCode, fullName, email, currentSubjectCorrectedCodes, repeatedCorrectedCodesJson);

                try (Statement updateStatement = connection.createStatement()) {
                    String updateQuery = String.format("""
                            UPDATE %s
                            SET right_code_2 = '%s'
                            WHERE code = '%s'
                            """, subject, resultCode, inputCode);
                    updateStatement.executeUpdate(updateQuery);
                    System.out.println(studyYear + " -> " + subject + " -> inputCode: " + inputCode + " to: " + resultCode + " (name: " + fullName + ", email: " + email + ")");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addCodeToReport(StudyYear1444 studyYear, String subject, String resultCode, String fullName, String email, List<String> currentSubjectCodes, Map<String, Map<String, Map<String, List<Person>>>> repeatedCodesJson) {
        if (resultCode.equals("")) {
            return;
        }

        if (currentSubjectCodes.contains(resultCode)) {
            repeatedCodesJson.get(studyYear.name()).get(subject).get(resultCode).add(new Person(fullName, email));
        } else {
            currentSubjectCodes.add(resultCode);
            repeatedCodesJson.get(studyYear.name()).get(subject).put(resultCode, new ArrayList<>());
            repeatedCodesJson.get(studyYear.name()).get(subject).get(resultCode).add(new Person(fullName, email));
        }
    }

    private static String validateCode(String resultCode, String resultEmail, String resultFullName, StudyYear1444 studyYear) {
        Student student = CodeFinder.getStudent(resultCode, studyYear);
        if (student == null) {
            return "";
        }
        String studentEmail = student.getEmail();


        if (studentEmail != null && (studentEmail.equals(resultEmail) ||
                studentEmail.split("@")[0].equals(resultEmail.split("@")[0]))) {
            return resultCode;
        }

        String studentFullName = student.getName();
        if (studentFullName == null) {
            return "";
        }

        studentFullName = studentFullName.replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه");

        resultFullName = resultFullName.replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه");

        if (studentFullName.equals(resultFullName)) {
            return resultCode;
        }

        LinkedHashSet<String> studentFullNameSet = new LinkedHashSet<>(Arrays.asList(studentFullName.split(" ")));
        LinkedHashSet<String> resultFullNameSet = new LinkedHashSet<>(Arrays.asList(resultFullName.split(" ")));

        if (studentFullNameSet.size() < 2 || resultFullNameSet.size() < 2 ||
                !studentFullNameSet.iterator().next().equals(resultFullNameSet.iterator().next())) {
            return "";
        }


        //try to find the same name
        int matchCount = 0;
        for (var resultFullNamePart : resultFullNameSet) {
            for (var studentFullNamePart : studentFullNameSet) {
                if (studentFullNamePart.equals(resultFullNamePart) &&
                        !(studentFullNamePart.equals("بن") ||
                                studentFullNamePart.equals("بنت") ||
                                studentFullNamePart.equals("عبد") ||
                                studentFullNamePart.equals("ابو") ||
                                studentFullNamePart.equals("ابن") ||
                                studentFullNamePart.equals("ابنه") ||
                                studentFullNamePart.equals("ابنت"))) {
                    matchCount++;
                    break;
                }
            }
        }

        if (matchCount > 2) {
            return resultCode;
        }

        return "";
    }

    public static String searchCode(String email, String fullName, StudyYear1444 year) {
        String foundCode = CodeFinder.getCode(email, year);

        boolean isFullName = fullName.split(" ").length > 2;

        if (foundCode == null || foundCode.equals("") && isFullName) {
            foundCode = CodeFinder.getCode(fullName.replaceAll("\\s+", ""), year);
        }

        if ((foundCode == null || foundCode.equals("")) && isFullName) {
            foundCode = CodeFinder.getHigherCode(fullName.replaceAll("\\s+", ""), year);
        }
        if (foundCode == null || foundCode.equals("")) {
            foundCode = CodeFinder.getHigherCode(email, year);
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
        String code = inputCode.trim().toUpperCase()
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
                .replaceAll("\u200E", "")
                .replaceAll("o", "0")
                .replaceAll("O", "0");
        return code;
    }
}
