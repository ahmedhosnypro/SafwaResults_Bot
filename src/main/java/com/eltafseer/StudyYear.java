package com.eltafseer;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum StudyYear {
    FST_YEAR("الفرقة الأولى", "D:\\13-Projects\\bot2\\src\\main\\resources\\fstYearResults.db"),
    SND_YEAR("الفرقة الثانية", "D:\\13-Projects\\bot2\\src\\main\\resources\\sndYearResults.db"),
    TRD_YEAR("الفرقة الثالثة", "D:\\13-Projects\\bot2\\src\\main\\resources\\TrdYearResults.db"),
    ERROR("حدث خطأ ما", "");

    private final String arabicNotation;
    private final String url;

    StudyYear(String arabicNotation, String url) {
        this.arabicNotation = arabicNotation;
        this.url = url;
    }

    static StudyYear getByOrdinal(int ordinal) {
        Optional<StudyYear> opt = Arrays.stream(StudyYear.values()).filter(s -> s.ordinal() == ordinal).findFirst();
        return opt.orElse(ERROR);
    }

    public String getArabicNotation() {
        return arabicNotation;
    }

    public String getUrl() {
        return url;
    }

    static StudyYear getByArabicNotation(String arabicNotation) {
        Optional<StudyYear> opt = Arrays.stream(StudyYear.values()).filter(s -> Objects.equals(s.getArabicNotation(), arabicNotation)).findFirst();
        return opt.orElse(ERROR);
    }
}
