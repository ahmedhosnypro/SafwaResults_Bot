package com.safwah.database.result._1443;

import com.safwah.Main;
import com.safwah.logger.Logger;
import com.safwah.study.year.university.StudyYear;
import com.safwah.study.year.YearsSubject;
import kotlin.Pair;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.safwah.study.year.university.StudyYear.listSubjects;

public class ResultsDataBase {
    private ResultsDataBase() {
    }

    private static final String JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static final SQLiteDataSource sndDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource trdDataSource = new SQLiteDataSource();
    private static final SQLiteDataSource fthDataSource = new SQLiteDataSource();

    public static final Connection sndYearConnection;
    public static final Connection trdYearConnection;
    public static final Connection fthYearConnection;

    static {
        sndDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.SND_YEAR.getDataBaseName());
        trdDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.TRD_YEAR.getDataBaseName());
        fthDataSource.setUrl(JDBC_URL_PREFIX + Main.getResourcePath() + StudyYear.FTH_YEAR.getDataBaseName());
        try {
            sndYearConnection = sndDataSource.getConnection();
            trdYearConnection = trdDataSource.getConnection();
            fthYearConnection = fthDataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedHashMap<? extends YearsSubject, String> getResults(StudyYear studyYear, String term, String nameOrEmail) {

        LinkedHashMap<YearsSubject, String> scores = new LinkedHashMap<>();

        var subjects = listSubjects(studyYear, term);

        switch (term) {
            case "fst" -> term = "1";
            case "snd" -> term = "2";
        }

        for (var subject : subjects) {
            Pair<? extends YearsSubject, String> result = null;
//            if (studyYear == StudyYear.FST_YEAR &&
//                    (subject.getEnglishName() + term + "_" + "1").equals("tagwid1_1")) {
//                String score = ExternalResults.getResult(nameOrEmail, 6);
//                result = new Pair<>(FstYearSubjects.TAGWID, score);
//            } else
//                if (studyYear == StudyYear.FST_YEAR &&
//                    (subject.getEnglishName() + term + "_" + "1").equals("aqida1_1")) {
//                String score = ExternalResults.getResult(nameOrEmail, 5);
//                result = new Pair<>(FstYearSubjects.AQIDA, score);
//            } else
//            {
            result = getResult(studyYear, subject, term, "1", nameOrEmail);
//            }

            if (result != null) {
                fstAndSndStageResult(studyYear, term, nameOrEmail, scores, subject, result);
            } else {
                result = getResult(studyYear, subject, term, "2", nameOrEmail);
                if (result != null) {
                    scores.put(subject, result.getSecond());
                }
            }
        }

        return scores;
    }

    public static void writeUsers() {
        var fstUsers = getUsers(StudyYear.SND_YEAR);
        var sndUsers = getUsers(StudyYear.TRD_YEAR);
        var trdUsers = getUsers(StudyYear.FTH_YEAR);

//        for (var trdUser : trdUsers.keySet()) {
//            fstUsers.remove(trdUser);
//            sndUsers.remove(trdUser);
//        }
//
//        for (var sndUser : sndUsers.keySet()) {
//            fstUsers.remove(sndUser);
//        }

        writeUsers(fstUsers, "snd_names.db", "AC");
        writeUsers(sndUsers, "trd_names.db", "AB");
        writeUsers(trdUsers, "4th_names.db", "AA");
    }

    private static void writeUsers(Map<String, String> users, String dataBaseName, String codePrefix) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(JDBC_URL_PREFIX + "D:\\SafwahResult_bot\\resources\\" + dataBaseName);

        int count = 1;
        String code;
        try (Connection fstConnection = dataSource.getConnection();
             Statement statement = fstConnection.createStatement()) {
            for (var entry : users.entrySet()) {
                code = codePrefix + "0".repeat(5 - String.valueOf(count).length()) + count;
                statement.executeUpdate("INSERT INTO users VALUES ('" +
                        entry.getKey().replaceAll("'", "") +
                        "', '" + entry.getValue().replaceAll("'", "") +
                        "', '" + code + "')");
                count++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getUsers(StudyYear studyYear) {

        Map<String, String> users = new LinkedHashMap<>();
        var subjects1 = listSubjects(studyYear, "fst");
        var subjects2 = listSubjects(studyYear, "snd");

        for (var subject : subjects1) {
            users.putAll(getUsers(studyYear, subject, "1", "1"));
        }

        for (var subject : subjects2) {
            users.putAll(getUsers(studyYear, subject, "2", "1"));
        }
        return users;
    }

    private static void fstAndSndStageResult(StudyYear studyYear, String term, String nameOrEmail, LinkedHashMap<YearsSubject, String> scores,
                                             YearsSubject subject, Pair<? extends YearsSubject, String> result) {
        int score = 0;
        int fullScore = 0;
        try {
            var s = result.getSecond().replaceAll("\\s+", "").split("/");
            score = Integer.parseInt(s[0].split("[.]")[0]);
            fullScore = Integer.parseInt(s[1]);
        } catch (Exception e) {
            // ignore
        }
        if (score < fullScore / 2 || score == 0) {
            var sndStageResult = getResult(studyYear, subject, term, "2", nameOrEmail);
            if (sndStageResult != null) {
                int sndScore = 0;
                try {
                    var s = sndStageResult.getSecond().replaceAll("\\s+", "").split("/");
                    sndScore = Integer.parseInt(s[0].split("[.]")[0]);
                } catch (Exception e) {
                    // ignore
                }
                if (sndScore > score) {
                    scores.put(subject, sndStageResult.getSecond());
                } else {
                    scores.put(subject, result.getSecond());
                }
            }
        } else {
            scores.put(subject, result.getSecond());
        }
    }

    private static Pair<? extends YearsSubject, String> getResult(StudyYear studyYear, YearsSubject subjects, String term, String stage, String nameOrEmail) {
        Connection con = switch (studyYear) {
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> fthYearConnection;
            default -> sndYearConnection;
        };
        return getResults(subjects, term, stage, nameOrEmail, con);
    }

    private static Map<String, String> getUsers(StudyYear studyYear, YearsSubject subjects, String term, String stage) {
        Connection con = switch (studyYear) {
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> fthYearConnection;
            default -> sndYearConnection;
        };
        return getUsers(subjects, term, stage, con);
    }

    private static Pair<? extends YearsSubject, String> getResults(YearsSubject subject, String term, String stage, String nameOrEmail, Connection con) {
        String getResultQuery = String.format("""
                        SELECT *
                        FROM %s
                        where trim(
                                      replace(
                                              replace(
                                                      replace(
                                                              replace(
                                                                      replace(
                                                                              replace(
                                                                                      replace(
                                                                                              replace(fullName, 'إ', 'ا'),
                                                                                              'أ', 'ا'),
                                                                                      'ى', 'ي'),
                                                                              'ة', 'ه'),
                                                                      'ؤ', 'و'),
                                                              'آ', 'ا'),
                                                      '  ', ' '),
                                              '   ', ' ')) like '%%%s%%'
                           OR lower(email) LIKE '%%%s%%'
                           """, subject.getEnglishName() + term + "_" + stage,
                nameOrEmail.replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه"),
                nameOrEmail.toLowerCase()
        );
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);
            if (resultSet.next()) {
                return new Pair<>(subject, resultSet.getString("totalScore"));
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return new Pair<>(subject, "0 / غائب");
    }

    private static Map<String, String> getUsers(YearsSubject subject, String term, String stage, Connection con) {
        Map<String, String> names = new HashMap<>();
        String getResultQuery = String.format("""
                SELECT *
                FROM %s
                   """, subject.getEnglishName() + term + "_" + stage);
        try (Statement statement = con.createStatement()) {
            var resultSet = statement.executeQuery(getResultQuery);

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("fullName");
                if (names.containsKey(email) && names.get(email).length() < name.length()) {
                    names.put(email, name);
                } else {
                    names.putIfAbsent(email, name);
                }
            }
        } catch (SQLException e) {
            Logger.log(e.getMessage());
            throw new RuntimeException(e);
        }
        return names;
    }

    public static boolean isNotExists(StudyYear studyYear, String nameOrEmail) {
        Connection con = switch (studyYear) {
            case TRD_YEAR -> trdYearConnection;
            case FTH_YEAR -> fthYearConnection;
            default -> sndYearConnection;
        };

        List<? extends YearsSubject> subjects = listSubjects(studyYear);
        for (var subject : subjects) {
            switch (subject.getTerm()) {
                case "fst" -> {
                    if (isNotExists(subject, "1", "1", nameOrEmail, con) ||
                            isNotExists(subject, "1", "2", nameOrEmail, con)) {
                        return false;
                    }
                }
                case "snd" -> {
                    if (isNotExists(subject, "2", "1", nameOrEmail, con) ||
                            isNotExists(subject, "2", "2", nameOrEmail, con)) {
                        return false;
                    }
                }
                case "both" -> {
                    if (isNotExists(subject, "1", "1", nameOrEmail, con) ||
                            isNotExists(subject, "1", "2", nameOrEmail, con) ||
                            isNotExists(subject, "2", "1", nameOrEmail, con) ||
                            isNotExists(subject, "2", "2", nameOrEmail, con)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private static boolean isNotExists(YearsSubject subject, String term, String stage, String nameOrEmail, Connection con) {
        String isExistQuery = String.format("""
                        SELECT *
                        FROM %s
                        where trim(
                                      replace(
                                              replace(
                                                      replace(
                                                              replace(
                                                                      replace(
                                                                              replace(
                                                                                      replace(
                                                                                              replace(fullName, 'إ', 'ا'),
                                                                                              'أ', 'ا'),
                                                                                      'ى', 'ي'),
                                                                              'ة', 'ه'),
                                                                      'ؤ', 'و'),
                                                              'آ', 'ا'),
                                                      '  ', ' '),
                                              '   ', ' ')) like '%%%s%%'
                           OR lower(email) LIKE '%%%s%%'
                           """, subject.getEnglishName() + term + "_" + stage,
                nameOrEmail.replaceAll("[أإآ]", "ا")
                        .replaceAll("ؤ", "و")
                        .replaceAll("ى", "ي")
                        .replaceAll("ة", "ه"),
                nameOrEmail.toLowerCase()
        );
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
