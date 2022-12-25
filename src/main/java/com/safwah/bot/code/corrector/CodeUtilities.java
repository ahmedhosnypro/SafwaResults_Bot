package com.safwah.bot.code.corrector;

import com.safwah.Person;
import com.safwah.study.year.StudyYear1444;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CodeUtilities {
    static final Pattern INVALID_CODE_PATTERN = Pattern.compile("^([e-zE-Z])");
    static int codeLength = 7;
    static String fstCodePrefix = "AD";
    static String sndCodePrefix = "AC";
    static String trdCodePrefix = "AB";
    static String fthCodePrefix = "AA";

    private static final String CURRENT_SUBJECT_CODES_PATH_NAME = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\code\\repeated_codes.json";
    private static final String CURRENT_SUBJECT_CORRECTED_CODES_PATH_NAME = "D:\\13-Projects\\SafwaResults_Bot\\src\\main\\resources\\code\\repeated_corrected_codes.json";

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

    static void reportRepeatedCodes() {
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

    @NotNull
    public static String getFormattedFullName(String inputFullName) {
        return inputFullName.trim()
                .replaceAll("[أإآ]", "ا")
                .replaceAll("ؤ", "و")
                .replaceAll("ى", "ي")
                .replaceAll("ی", "ي")
                .replaceAll("ة", "ه")
                .replaceAll("[ً-ْ]", "")
                .replaceAll("'", "''")
                .replaceAll("عبد ال", "عبدال")
                .replaceAll("[-،._]|\\s+", " ");
    }
}
