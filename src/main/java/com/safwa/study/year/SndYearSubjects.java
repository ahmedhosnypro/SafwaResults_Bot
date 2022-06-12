package com.safwa.study.year;

public enum SndYearSubjects implements YearsSubjects {
    ADAAB("التربية والسلوك", "adaab"),
    AQIDA("العقيدة الإسلامية", "aqida"),
    FIQH_HANAFY("الفقه الحنفي", "fiqhHanafy"),
    FIQH_HANBALY("الفقه الحنبلي", "fiqhHanbaly"),
    FIQH_MALKY("الفقه المالكي", "fiqhMalky"),
    FIQH_SHAFEEY("الفقه الشافعي", "fiqhShafeey"),
    MERATH("الميراث", "merath"),
    OLOOM_QURAN("علوم القرآن", "oloomQuran"),
    SARF("الصرف", "sarf"),
    SERA("السيرة", "sera"),
    TAFSEER("التفسير", "tafseer");

    private final String arabicName;
    private final String englishName;

    SndYearSubjects(String arabicName, String englishName) {
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
