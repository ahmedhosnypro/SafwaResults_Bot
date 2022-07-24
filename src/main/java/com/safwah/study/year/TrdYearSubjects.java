package com.safwah.study.year;

public enum TrdYearSubjects implements YearsSubjects {
    ADAAB("التربية والسلوك", "adaab","both", 50),
    AQIDA("العقيدة الإسلامية", "aqida", "both", 50),
    ARABIC("النحو", "arabic", "both", 50),
    EMLAA("الإملاء", "emlaa", "snd", 50),
    FERAQ("الفرق والمذاهب", "feraq", "both", 50),
    FIQH("الفقه العام", "fiqh", "both", 50),
    FIQH_HANAFY("الفقه الحنفي", "fiqhHanafy", "both", 50),
    FIQH_HANBALY("الفقه الحنبلي", "fiqhHanbaly", "both", 50),
    FIQH_MALKY("الفقه المالكي", "fiqhMalky", "both", 50),
    FIQH_SHAFEEY("الفقه الشافعي", "fiqhShafeey", "both", 50),
    HISTORY("التاريخ", "history", "both", 50),
    MOSTALH_HADITH("مصطلح الحديث", "mostalhHadith", "both", 50),
    OSOOL("أصول الفقه", "osool", "fst", 50),
    TAFSEER("التفسير", "tafseer", "fst", 100),
    TAGWID("التجويد", "tagwid", "fst", 50);

    private final String arabicName;
    private final String englishName;
    private final String term;

    private  final  int score;
    TrdYearSubjects(String arabicName, String englishName, String term, int score) {
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
