package com.safwah.study.year.university;

import com.safwah.study.year.YearsSubject;

public enum TrdYearSubject implements YearsSubject {
    ADAAB("التربية والسلوك", "adaab", "both", 50, true, true),
    AQIDA("العقيدة", "aqida", "both", 50, true, true),
    FIQH("الفقه العام", "fiqh", "both", 50, true, true),
    FIQH_HANAFY("الفقه الحنفي", "fiqhHanafy", "both", 50, true, true),
    FIQH_HANBALY("الفقه الحنبلي", "fiqhHanbaly", "both", 50, true, true),
    FIQH_MALKY("الفقه المالكي", "fiqhMalky", "both", 50, true, true),
    FIQH_SHAFEEY("الفقه الشافعي", "fiqhShafeey", "both", 50, true, true),
    MOSTALH_HADITH("مصطلح الحديث", "mostalhHadith", "both", 50, true, true),
    FIQH_RULES("قواعد فقهية", "fiqhRules", "both", 50, true, true),
    TAFSEER("التفسير", "tafseer", "fst", 100, true, true),
    TAGWID("التجويد", "tagwid", "fst", 50, true, true),
    OSOOL("أصول الفقه", "osool", "fst", 50, true, true),
    FERAQ("الفرق والمذاهب", "feraq", "both", 50, true, true),
    HISTORY("التاريخ", "history", "both", 50, true, true),
    ARABIC_LANGUAGE("اللغة العربية", "arabic_language", "fst", 50, true, false),
    ARABIC("النحو", "arabic", "both", 50, false, false),
    EMLAA("الإملاء", "emlaa", "snd", 50, false, false);


    private final String arabicName;
    private final String englishName;
    private final String term;

    private final int score;
    private final boolean examined;
    private final boolean codeCorrected;

    TrdYearSubject(String arabicName, String englishName, String term, int score, boolean examined, boolean codeCorrected) {
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

    public int getScore() {
        return score;
    }

    public boolean isExamined() {
        return examined;
    }

    public boolean isCodeCorrected() {
        return codeCorrected;
    }
}
