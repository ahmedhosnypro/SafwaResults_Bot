package com.safwah.study.year;

public enum FstYearSubjects implements YearsSubjects {

    ADAAB("صحيح الآداب الإسلامية", "adaab", "both", 50),
    AQIDA("العقيدة الإسلامية", "aqida", "both", 50),
    FIQH("الفقه", "fiqh", "both", 50),
    HADITH("الحديث", "hadith", "both", 50),
    MOSTALH_HADITH("مصطلح الحديث", "mostalHadith", "fst", 100),
    NA7W("النحو", "na7w", "both", 50),
    OSOOL("أصول الفقه", "osool", "both", 50),
    TAGWID("التجويد", "tagwid", "both", 50);


    private final String arabicName;
    private final String englishName;
    private final String term;
    private final int score;

    FstYearSubjects(String arabicName, String englishName, String term, int score) {
        this.arabicName = arabicName;
        this.englishName = englishName;
        this.term = term;
        this.score = score;
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

    public int getScore() {
        return score;
    }
}