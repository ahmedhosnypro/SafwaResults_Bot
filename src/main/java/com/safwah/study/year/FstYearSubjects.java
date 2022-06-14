package com.safwah.study.year;

public enum FstYearSubjects implements YearsSubjects {
    ADAAB("صحيح الآداب الإسلامية", "adaab"),
    AQIDA("العقيدة الإسلامية", "aqida"),
    FIQH("الفقه", "fiqh"),
    HADITH("الحديث", "hadith"),
    NA7W("النحو", "na7w"),
    OSOOL("أصول الفقه", "osool"),
    TAGWID("التجويد", "tagwid");

    private final String arabicName;
    private final String englishName;

    FstYearSubjects(String arabicName, String englishName) {
        this.arabicName = arabicName;
        this.englishName = englishName;
    }

    public String getArabicName() {
        return arabicName;
    }

    public String getEnglishName() {
        return englishName;
    }
}