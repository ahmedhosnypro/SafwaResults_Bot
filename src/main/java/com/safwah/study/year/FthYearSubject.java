package com.safwah.study.year;

public enum FthYearSubject implements YearsSubject {
    ADAAB("التربية والسلوك", "adaab", "both", 50, true, true),
    AQIDA("العقيدة", "aqida", "both", 50, true, true),
    HADITH1("حديث [1]", "hadith1", "both", 50, true, true),
    HADITH2("حديث [2]", "hadith2", "both", 50, true, true),
    RECENT_PROBLEMS1("قضايا معاصرة [1]", "recent_problems1", "both", 50, true, true),
    RECENT_PROBLEMS2("قضايا معاصرة [2]", "recent_problems2", "both", 50, true, false),
    ARABIC_ADAAB("الأدب العربي", "arabic_adaab", "both", 50, true, false);


    private final String arabicName;
    private final String englishName;
    private final String term;
    private final int score;
    private final boolean examined;
    private final boolean codeCorrected;

    FthYearSubject(String arabicName, String englishName, String term, int score, boolean examined, boolean codeCorrected) {
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
