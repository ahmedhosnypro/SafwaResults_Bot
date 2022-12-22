package com.safwah.bot.code.corrector;

import com.safwah.Main;
import com.safwah.bot.code.CodeFinder;
import com.safwah.Person;
import com.safwah.Student;
import com.safwah.study.year.StudyYear1444;
import org.jetbrains.annotations.NotNull;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private static int count = 0;
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
        boolean isReport = false;
//        boolean isReport = true;
//        resetCorrectCode();


//        addColumn();

        Thread thread = new Thread(() -> {
            try {
                correctCode(isReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //time now var
        long endTime = System.currentTimeMillis();
        //calculate time in minutes
        long timeElapsed = (endTime - startTime) / 1000;
        System.out.println("Execution time in seconds: " + timeElapsed);
        System.out.println("Execution time in minutes: " + timeElapsed / 60);

    }

    private static void correctCode(boolean isReport) {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);

        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);

            var subjects = listNonCorrectCodeSubjects(studyYear);
//            var subjects = listExaminedSubjects(studyYear);
            for (var subject : subjects) {
                if (isReport) {
                    currentSubjectCodes = new ArrayList<>();
                    currentSubjectCorrectedCodes = new ArrayList<>();
                    repeatedCodesJson.get(studyYear.name()).put(subject.getEnglishName(), new LinkedHashMap<>());
                    repeatedCorrectedCodesJson.get(studyYear.name()).put(subject.getEnglishName(), new LinkedHashMap<>());
                }
                Main.EXECUTOR.submit(() -> {
                    correctCode(connection, studyYear, subject.getEnglishName(), isReport);
                });
            }
        }

        System.out.println(count);

        if (isReport) {
            // delete non repeated codes from repeatedCodesJson
            deleteNonRepeatedCodes(studyYears);

            //write to file
            reportRepeatedCodes();
        }

    }

    private static void correctCode(Connection connection, StudyYear1444 studyYear, String subject, boolean isReport) {
//        String codeListQuery = String.format("""
//                SELECT code, fullName, email, right_code_2
//                FROM %s
//                """, subject);

        //skip corrected codes when retrying
        String codeListQuery = String.format("""
                SELECT code, fullName, email, right_code_2
                FROM %s
                WHERE right_code_2 is null or right_code_2 not like 'A%%'
                """, subject);

        // repair code for a student
//        String codeListQuery = String.format("""
//                SELECT code, fullName, email, right_code_2
//                FROM %s
//                WHERE right_code_2 = 'AB02616'
//                """, subject);

        String updateCodeQuery = "UPDATE " + subject + " SET right_code_2=?, partial_match=? " +
                "WHERE code=? and fullName=? and email=?";

        try (Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(codeListQuery);

            while (resultSet.next()) {
                count++;
                String inputCode = resultSet.getString("code");
                String inputFullName = resultSet.getString("fullName");
                String inputEmail = resultSet.getString("email");

                //skip corrected codes when retrying
//                String rightCode2 = resultSet.getString("right_code_2");
//                if (!(rightCode2 == null || rightCode2.isBlank() || rightCode2.isEmpty())) {
//                    continue;
//                }


                //debugging
//                if (!inputEmail.equals("sausannahdie@gmail.com")) {
//                    continue;
//                }

//                if (!inputFullName.equals("عيد سيد على خليل")) {
//                    continue;
//                }

                Main.EXECUTOR.submit(() -> {
                    String resultCode = formatCode(inputCode);
                    String formattedFullName = getFormattedFullName(inputFullName);


                    String formattedEmail = inputEmail.trim()
                            .replaceAll("'", "''");

                    String[] result = findTheRightCode(studyYear, resultCode, formattedFullName, formattedEmail);
                    resultCode = result[0];
                    String partialMatch = result[1];

                    updateCode(connection, updateCodeQuery, inputCode, inputFullName, inputEmail, resultCode, partialMatch);
                    System.out.println(studyYear + " -> " + subject + " -> inputCode: " + inputCode + " to: " + resultCode + " (name: " + inputFullName + ", email: " + inputEmail + ")");
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static String getFormattedFullName(String inputFullName) {
        return inputFullName.trim()
                .replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه")
                .replaceAll("[ً-ْ]", "")
                .replaceAll("'", "''")
                .replaceAll("عبد ال", "عبدال")
                .replaceAll("[-،._]|\\s+", " ");
    }

    @NotNull
    private static String[] findTheRightCode(StudyYear1444 studyYear, String resultCode, String formattedFullName, String formattedEmail) {
        String partialMatch = "0";
        if (!isValidCode(resultCode)) {
            String[] result = searchCode(formattedEmail, formattedFullName, studyYear);
            resultCode = result[0];
            partialMatch = result[1];
        } else {
            resultCode = validateCode(resultCode, formattedEmail, formattedFullName, studyYear);
        }


        if (resultCode.equals("")) {
            String[] result = searchCode(formattedEmail, formattedFullName, studyYear);
            resultCode = result[0];
            partialMatch = result[1];
        }
        return new String[]{resultCode, partialMatch};
    }

    private static void updateCode(Connection connection, String updateCodeQuery, String inputCode, String inputFullName, String inputEmail, String resultCode, String partialMatch) {
        if (!resultCode.equals("")) {
            try (PreparedStatement updateStatement = connection.prepareStatement(updateCodeQuery)) {
                updateStatement.setString(1, resultCode);
                updateStatement.setString(2, partialMatch);
                updateStatement.setString(3, inputCode);
                updateStatement.setString(4, inputFullName);
                updateStatement.setString(5, inputEmail);
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        studentFullName = studentFullName.trim().replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ة", "ه")
                .replaceAll("[ً-ْ]", "")
                .replaceAll("'", "''")
                .replaceAll("[-،._]|\\s+", " ");

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
        matchCount = getMatchCount(studentFullNameSet, resultFullNameSet, matchCount);

        //todo
        if (matchCount > 2) {
            return resultCode;
        }

        return "";
    }

    public static int getMatchCount(Set<String> studentFullNameSet, Set<String> resultFullNameSet, int matchCount) {
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
        return matchCount;
    }

    public static String[] searchCode(String email, String fullName, StudyYear1444 year) {
        boolean isPartialMatch = false;
        String[] foundCode = CodeFinder.getCode(email, year);

        boolean isFullName = fullName.split(" ").length > 2;

        if (foundCode == null && isFullName) {
            foundCode = CodeFinder.getCode(fullName, year);
        }

        if (foundCode == null && isFullName) {
            foundCode = CodeFinder.getHigherCode(fullName, year);
        }
        if (foundCode == null) {
            foundCode = CodeFinder.getHigherCode(email, year);
        }

        if (foundCode == null && isFullName) {
            foundCode = CodeFinder.getCodeByTryingMatchingNames(fullName, year);
            if (foundCode != null) {
                isPartialMatch = true;
            }
        }

        if (foundCode == null) {
            foundCode = new String[]{"", ""};
        }
        return new String[]{foundCode[1], isPartialMatch ? "1" : "0"};
    }

    private static void addColumn() {
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);
            var subjects = listNonCorrectCodeSubjects(studyYear);
            for (var subject : subjects) {
                addColumn(connection, subject.getEnglishName(), "right_code");
                addColumn(connection, subject.getEnglishName(), "right_code_2");
                addColumn(connection, subject.getEnglishName(), "partial_match");
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
                code.substring(2).matches("\\d{5}") &&
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
                .replaceAll("\u200E", "")
                .replaceAll("o", "0")
                .replaceAll("O", "0");
    }


    private static void deleteNonRepeatedCodes(List<StudyYear1444> studyYears) {
        for (var studyYear : studyYears) {
            for (var subject : listExaminedSubjects(studyYear)) {
                repeatedCodesJson.get(studyYear.name()).get(subject.getEnglishName())
                        .entrySet().removeIf(code -> code.getValue().size() == 1);

                repeatedCorrectedCodesJson.get(studyYear.name()).get(subject.getEnglishName())
                        .entrySet().removeIf(code -> code.getValue().size() == 1);
            }
        }
    }

    private static void reportRepeatedCodes() {
        Path currentSubjectCodesPath = Paths.get(CURRENT_SUBJECT_CODES_PATH_NAME);
        Path currentSubjectCorrectedCodesPath = Paths.get(CURRENT_SUBJECT_CORRECTED_CODES_PATH_NAME);
        try (Writer repeatedCodesJsonWriter = Files.newBufferedWriter(currentSubjectCodesPath, StandardCharsets.UTF_8);
             Writer repeatedCorrectedCodesJsonWriter = Files.newBufferedWriter(currentSubjectCorrectedCodesPath, StandardCharsets.UTF_8)) {
            //    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String repeatedCodesJsonString = gson.toJson(repeatedCodesJson);
//            String repeatedCorrectedCodesJsonString = gson.toJson(repeatedCorrectedCodesJson);
//            repeatedCodesJsonWriter.write(repeatedCodesJsonString);
//            repeatedCorrectedCodesJsonWriter.write(repeatedCorrectedCodesJsonString);
        } catch (IOException e) {
            e.printStackTrace();
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

    private static void addColumn(Connection connection, String englishName, String columnName) {
        try (Statement statement = connection.createStatement()) {
            String sql = "ALTER TABLE " + englishName + " ADD  " + columnName + " TEXT";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void resetCorrectCode(Connection connection, StudyYear1444 studyYear, String subject) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("UPDATE " + subject + " SET right_code_2 = '', partial_match = ''");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
