package com.safwah.study.year.university;

import com.safwah.study.year.YearsSubject;

public enum SndYearSubject implements YearsSubject {
    ADAAB("التربية والسلوك", "adaab", "both", 50, true, true),
    AQIDA("العقيدة الإسلامية", "aqida", "both", 50, true, true),
    HISTORY("تاريخ التشريع", "history", "fst", 50, true, true),
    FIQH_HANAFY("الفقه الحنفي", "fiqhHanafy", "both", 50, true, true),
    FIQH_HANBALY("الفقه الحنبلي", "fiqhHanbaly", "both", 50, true, true),
    FIQH_MALKY("الفقه المالكي", "fiqhMalky", "both", 50, true, true),
    FIQH_SHAFEEY("الفقه الشافعي", "fiqhShafeey", "both", 50, true, true),
    TAFSEER("التفسير", "tafseer", "both", 50, true, true),
    BALAGHA("البلاغة", "balagha", "fst", 100, true, true),
    SERA("السيرة", "sera", "both", 50, true, true),
    OLOOM_QURAN("علوم القرآن", "oloomQuran", "both", 50, true, true),
    MERATH("الميراث", "merath", "snd", 50, false, false),

    SARF("الصرف", "sarf", "snd", 50, false, false);



    private final String arabicName;
    private final String englishName;
    private final String term;

    private  final  int score;
    private final boolean examined;
    private final boolean codeCorrected;
    SndYearSubject(String arabicName, String englishName, String term, int score, boolean examined, boolean codeCorrected) {
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
