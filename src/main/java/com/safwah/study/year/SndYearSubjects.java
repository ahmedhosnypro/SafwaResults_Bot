package com.safwah.study.year;

public enum SndYearSubjects implements YearsSubjects {
    ADAAB("التربية والسلوك", "adaab", "both", 50),
    AQIDA("العقيدة الإسلامية", "aqida", "both", 50),
    BALAGHA("البلاغة", "balagha", "fst", 100),
    FIQH_HANAFY("الفقه الحنفي", "fiqhHanafy", "both", 50),
    FIQH_HANBALY("الفقه الحنبلي", "fiqhHanbaly", "both", 50),
    FIQH_MALKY("الفقه المالكي", "fiqhMalky", "both", 50),
    FIQH_SHAFEEY("الفقه الشافعي", "fiqhShafeey", "both", 50),
    HISTORY("التاريخ", "history", "fst", 50),
    MERATH("الميراث", "merath", "snd", 50),
    OLOOM_QURAN("علوم القرآن", "oloomQuran", "both", 50),
    SARF("الصرف", "sarf", "snd", 50),
    SERA("السيرة", "sera", "both", 50),
    TAFSEER("التفسير", "tafseer", "both", 50);

    private final String arabicName;
    private final String englishName;
    private final String term;

    private  final  int score;

    SndYearSubjects(String arabicName, String englishName, String term, int score) {
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
