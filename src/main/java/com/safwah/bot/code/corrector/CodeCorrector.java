package com.safwah.bot.code.corrector;

import com.safwah.Main;
import com.safwah.study.year.university.StudyYear1444;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static com.safwah.bot.code.corrector.CodeFinder.*;
import static com.safwah.bot.code.corrector.DataBaseEditor.*;
import static com.safwah.bot.code.corrector.CodeUtilities.*;
import static com.safwah.study.year.university.StudyYear1444.*;


public class CodeCorrector {


    private static int count = 0;


    public static void main(String[] args) {
        correctCode();
    }

    static void correctCode(){
        //time now var
        long startTime = System.currentTimeMillis();
        boolean isReport = false;

//        boolean isReport = true;
//        resetCorrectCode();
        addColumn();

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

        System.out.println("count" + count);

        //time now var
        long endTime = System.currentTimeMillis();
        //calculate time in minutes
        long timeElapsed = (endTime - startTime) / 1000;
        System.out.println("Execution time in seconds: " + timeElapsed);
        System.out.println("Execution time in minutes: " + timeElapsed / 60);
    }

     static void correctCode(Connection connection, StudyYear1444 studyYear, String subject, boolean isReport) {
//        String codeListQuery = String.format("""
//                SELECT code, fullName, email, right_code_2
//                FROM %s
//                """, subject);

//        skip corrected codes when retrying
        String codeListQuery = String.format("""
                SELECT code, fullName, email, right_code_2
                FROM %s
                WHERE right_code_2 is null or right_code_2 not like 'A%%'
                """, subject);

//         repair code for a student
//        String codeListQuery = String.format("""
//                SELECT code, fullName, email, right_code_2
//                FROM %s
//                WHERE fullName = 'خديجة محمد عبدالحميد عبدالحميد كيرة\s'
//                """, subject);

        String updateCodeQuery = "UPDATE " + subject + " SET right_code_2=?, partial_match=? " +
                "WHERE code=? and fullName=? and email=?";

        try (Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(codeListQuery);

            while (resultSet.next()) {
                String inputCode = resultSet.getString("code");
                String inputFullName = resultSet.getString("fullName");
                String inputEmail = resultSet.getString("email");

                {
                    //skip corrected codes when retrying
//                String rightCode2 = resultSet.getString("right_code_2");
//                if (!(rightCode2 == null || rightCode2.isBlank() || rightCode2.isEmpty())) {
//                    continue;
//                }


                    //debugging
//                if (!inputEmail.equals("sausannahdie@gmail.com")) {
//                    continue;
//                }

//                if (!inputFullName.equals("امنه عمر عبدالكريم حسن")) {
//                    continue;
//                }
                }

                Main.EXECUTOR.submit(() -> {
                    String resultCode = formatCode(inputCode);
                    String formattedFullName = getFormattedFullName(inputFullName);


                    String formattedEmail = inputEmail.trim()
                            .toLowerCase()
                            .replaceAll("'", "''");

                    String[] result = findTheRightCode(studyYear, resultCode, formattedFullName, formattedEmail);
                    resultCode = result[0];
                    String partialMatch = result[1];

                    updateCode(connection, updateCodeQuery, inputCode, inputFullName, inputEmail, resultCode, partialMatch);
                    count++;
                    System.out.println("count " + count + " " + studyYear + " -> " + subject + " -> inputCode: " + inputCode + " to: " + resultCode + " (name: " + inputFullName + ", email: " + inputEmail + ")");
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void correctCode(boolean isReport) {
//        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        List<StudyYear1444> studyYears = List.of(FST_YEAR, SND_YEAR, TRD_YEAR, FTH_YEAR);
        for (var studyYear : studyYears) {
            Connection connection = getConnection(studyYear);

            var subjects = listExaminedSubjects(studyYear);
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
        {
            if (isReport) {
                // delete non repeated codes from repeatedCodesJson
                deleteNonRepeatedCodes(studyYears);

                //write to file
                reportRepeatedCodes();
            }
        }
    }
}
