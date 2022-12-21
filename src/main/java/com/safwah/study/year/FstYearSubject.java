package com.safwah.study.year;

public enum FstYearSubject implements YearsSubject {
    AQIDA("العقيدة الإسلامية", "aqida", "both", 50, true, false),
    TAGWID("التجويد", "tagwid", "both", 50, true, true),
    NA7W("النحو", "na7w", "both", 50, true, true),
    HADITH("الحديث", "hadith", "both", 50, true, true),
    OSOOL("أصول الفقه", "osool", "both", 50, true, true),
    FIQH("الفقه", "fiqh", "both", 50, true, true),
    ADAAB("الآداب الإسلامية", "adaab", "both", 50, false, false),
    MOSTALH_HADITH("مصطلح الحديث", "mostalHadith", "fst", 100, false, false);


    private final String arabicName;
    private final String englishName;
    private final String term;
    private final int score;
    private final boolean examined;
    private final boolean codeCorrected;

    FstYearSubject(String arabicName, String englishName, String term, int score, boolean examined, boolean codeCorrected) {
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