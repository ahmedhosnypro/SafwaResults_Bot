package com.safwah.study.year;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum StudyYear {
    FST_YEAR("الفرقة الأولى", "fstYearResults.db"),
    SND_YEAR("الفرقة الثانية", "sndYearResults.db"),
    TRD_YEAR("الفرقة الثالثة", "TrdYearResults.db"),
    ERROR("حدث خطأ ما", "");


    private final String arabicNotation;
    private final String dataBaseName;

    StudyYear(String arabicNotation, String url) {
        this.arabicNotation = arabicNotation;
        this.dataBaseName = url;
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

    public static StudyYear getByArabicNotation(String arabicNotation) {
        Optional<StudyYear> opt = Arrays.stream(StudyYear.values()).filter(s -> Objects.equals(s.getArabicNotation(), arabicNotation)).findFirst();
        return opt.orElse(ERROR);
    }
}
