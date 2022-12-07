package com.safwah.study.year;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum StudyYear {
    NEW_YEAR("", "", "fst_names.db"),
    FST_YEAR("الفرقة الأولى", "fstYearResults.db", "snd_names.db"),
    SND_YEAR("الفرقة الثانية", "sndYearResults.db", "trd_names.db"),
    TRD_YEAR("الفرقة الثالثة", "TrdYearResults.db", "4th_names.db"),
    ERROR("حدث خطأ ما", "", "");


    private final String arabicNotation;
    private final String dataBaseName;
    private final String codeDataBaseName;

    StudyYear(String arabicNotation, String url, String codeDataBaseName) {
        this.arabicNotation = arabicNotation;
        this.dataBaseName = url;
        this.codeDataBaseName = codeDataBaseName;
    }

    public static StudyYear getByOrdinal(int ordinal) {
        Optional<StudyYear> opt = Arrays.stream(StudyYear.values()).filter(s -> s.ordinal() == ordinal).findFirst();
        return opt.orElse(ERROR);
    }

    public String getArabicNotation() {
        return arabicNotation;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public String getCodeDataBaseName() {
        return codeDataBaseName;
    }

    public static StudyYear getByArabicNotation(String arabicNotation) {
        Optional<StudyYear> opt = Arrays.stream(StudyYear.values()).filter(s -> Objects.equals(s.getArabicNotation(), arabicNotation)).findFirst();
        return opt.orElse(ERROR);
    }
}
