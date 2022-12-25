package com.safwah.bot.code.corrector;

import com.safwah.study.year.StudyYear1444;

import java.util.Set;

import static com.safwah.bot.code.corrector.CodeValidator.validateCode;
import static com.safwah.bot.code.corrector.CodeUtilities.isValidCode;

public class CodeFinder {

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

    static String[] findTheRightCode(StudyYear1444 studyYear, String resultCode, String formattedFullName, String formattedEmail) {
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

    public static String[] searchCode(String email, String fullName, StudyYear1444 year) {
        boolean isPartialMatch = false;
        String[] foundCode = com.safwah.bot.code.bot.CodeFinder.getCode(email, year);

        boolean isFullName = fullName.split(" ").length > 2;

        if (foundCode == null) {
            foundCode = com.safwah.bot.code.bot.CodeFinder.getCode(fullName, year);
        }

        if (foundCode == null) {
            foundCode = com.safwah.bot.code.bot.CodeFinder.getHigherCode(fullName, year);
        }
        if (foundCode == null) {
            foundCode = com.safwah.bot.code.bot.CodeFinder.getHigherCode(email, year);
        }

        if (foundCode == null && isFullName) {
            foundCode = com.safwah.bot.code.bot.CodeFinder.getCodeByTryingMatchingNames(fullName, year, false);
            if (foundCode != null) {
                isPartialMatch = true;
            }
        }

        if (foundCode == null) {
            foundCode = new String[]{"", ""};
        }
        return new String[]{foundCode[1], isPartialMatch ? "1" : "0"};
    }
}
