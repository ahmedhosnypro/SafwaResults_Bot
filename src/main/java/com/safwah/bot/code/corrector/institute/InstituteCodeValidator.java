package com.safwah.bot.code.corrector.institute;

import com.safwah.Student;
import com.safwah.bot.code.bot.institute.InstituteCodeFinderBot;
import com.safwah.study.year.institute.InstituteStudyYear1444;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static com.safwah.bot.code.corrector.CodeFinder.getMatchCount;
import static com.safwah.bot.code.corrector.CodeUtilities.getFormattedFullName;

public class InstituteCodeValidator {
    public static String validateCode(String resultCode, String resultEmail, String resultFullName, InstituteStudyYear1444 studyYear) {
        Student student = InstituteCodeFinderBot.getStudent(resultCode, studyYear);
        if (student == null) {
            return "";
        }
        String studentEmail = student.getEmail();

        if (studentEmail.equals(resultEmail) ||
                studentEmail.split("@")[0].equals(resultEmail.split("@")[0])) {
            return resultCode;
        }


        String studentFullName = student.getName();
        if (studentFullName == null) {
            return "";
        }

        studentFullName = getFormattedFullName(studentFullName);

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
}
