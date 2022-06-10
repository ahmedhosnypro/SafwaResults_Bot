package com.eltafseer;

public enum TrdYearSubjects implements YearsSubjects {
    ADAAB("التربية والسلوك", "adaab"),
    AQIDA("العقيدة الإسلامية", "aqida"),
    ARABIC("النحو", "arabic"),
    EMLAA("الإملاء", "emlaa"),
    FERAQ("الفرق والمذاهب", "feraq"),
    FIQH("الفقه العام", "fiqh"),
    FIQH_HANAFY("الفقه الحنفي", "fiqhHanafy"),
    FIQH_HANBALY("الفقه الحنبلي", "fiqhHanbaly"),
    FIQH_MALKY("الفقه المالكي", "fiqhMalky"),
    FIQH_SHAFEEY("الفقه الشافعي", "fiqhShafeey"),
    HISTORY("علوم القرآن", "history"),
    MOSTALH_HADITH("الصرف", "mostalhHadith");

    private final String arabicName;
    private final String englishName;

    TrdYearSubjects(String arabicName, String englishName) {
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
