package com.safwah.bot.code.corrector.institute;

import com.safwah.bot.code.bot.institute.InstituteCodeFinderBot;
import com.safwah.study.year.institute.InstituteStudyYear1444;

import java.util.Set;

import static com.safwah.bot.code.corrector.CodeUtilities.isValidInstituteCode;
import static com.safwah.bot.code.corrector.institute.InstituteCodeValidator.validateCode;

public class InstituteCodeFinder {

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

    static String[] findTheRightCode(InstituteStudyYear1444 studyYear, String resultCode, String formattedFullName, String formattedEmail) {
        String partialMatch = "0";
        if (!isValidInstituteCode(resultCode)) {
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

    public static String[] searchCode(String email, String fullName, InstituteStudyYear1444 year) {
        boolean isPartialMatch = false;
        String[] foundCode = InstituteCodeFinderBot.getCode(email, year);

        boolean isFullName = fullName.split(" ").length > 2;

        if (foundCode == null) {
            foundCode = InstituteCodeFinderBot.getCode(fullName, year);
        }

        if (foundCode == null) {
            foundCode = InstituteCodeFinderBot.getHigherCode(fullName, year);
        }
        if (foundCode == null) {
            foundCode = InstituteCodeFinderBot.getHigherCode(email, year);
        }

        if (foundCode == null /*&& isFullName*/) {
            foundCode = InstituteCodeFinderBot.getCodeByTryingMatchingNames(fullName, year, false);
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
