package com.safwah.study.year.institute;

import com.safwah.study.year.YearsSubject;

public enum InstituteFstYearSubject implements YearsSubject {
    AQIDA("العقيدة الإسلامية", "aqida", "both", 50, true, true),

    OSOOL("أصول الفقه", "usool", "both", 50, true, true),
    FIQH("الفقه", "fiqh", "both", 50, true, true),
    ADAAB("الآداب الإسلامية", "adab", "both", 50, true, true),
    TAGWID("التجويد", "tagweed", "both", 50, true, true);


    private final String arabicName;
    private final String englishName;
    private final String term;
    private final int score;
    private final boolean examined;
    private final boolean codeCorrected;

    InstituteFstYearSubject(String arabicName, String englishName, String term, int score, boolean examined, boolean codeCorrected) {
        this.arabicName = arabicName;
        this.englishName = englishName;
        this.term = term;
        this.score = score;
        this.examined = examined;
        this.codeCorrected = codeCorrected;
    }

    public String getArabicName() {
        return arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getTerm() {
        return term;
    }

    public boolean isExamined() {
        return examined;
    }

    public int getScore() {
        return score;
    }

    public boolean isCodeCorrected() {
        return codeCorrected;
    }
}