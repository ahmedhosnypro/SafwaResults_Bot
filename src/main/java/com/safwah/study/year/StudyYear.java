package com.safwah.study.year;

import java.util.*;

public enum StudyYear {
    FST_YEAR("الفرقة الأولى", "", "fst_names.db"),
    SND_YEAR("الفرقة الثانية", "fstYearResults.db", "snd_names.db"),
    TRD_YEAR("الفرقة الثالثة", "sndYearResults.db", "trd_names.db"),
    FTH_YEAR("الفرقة الرابعة", "TrdYearResults.db", "4th_names.db"),
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

    public static List<? extends YearsSubject> listSubjects(StudyYear studyYear) {
        List<? extends YearsSubject> subjects;
        subjects = switch (studyYear) {
            case FTH_YEAR ->
                    Arrays.stream(FthYearSubject.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
            case TRD_YEAR ->
                    Arrays.stream(TrdYearSubject.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
            case SND_YEAR ->
                    Arrays.stream(SndYearSubject.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
            default -> Arrays.stream(FstYearSubject.values()).sorted(Comparator.comparing(Enum::ordinal)).toList();
        };
        return subjects;
    }

    public static List<? extends YearsSubject> listSubjects(StudyYear studyYear, String term) {
        List<? extends YearsSubject> subjects;
        subjects = switch (studyYear) {
            case FTH_YEAR -> Arrays.stream(FthYearSubject.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case TRD_YEAR -> Arrays.stream(TrdYearSubject.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case SND_YEAR -> Arrays.stream(SndYearSubject.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            default -> Arrays.stream(FstYearSubject.values())
                    .filter(subject -> subject.getTerm().equals("both") || subject.getTerm().equals(term))
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
        };
        return subjects;
    }

    public static List<? extends YearsSubject> listExaminedSubjects(StudyYear studyYear) {
        List<? extends YearsSubject> subjects;
        subjects = switch (studyYear) {
            case FTH_YEAR -> Arrays.stream(FthYearSubject.values())
                    .filter(subject -> subject.isExamined())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case TRD_YEAR -> Arrays.stream(TrdYearSubject.values())
                    .filter(subject -> subject.isExamined())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case SND_YEAR -> Arrays.stream(SndYearSubject.values())
                    .filter(subject -> subject.isExamined())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            default -> Arrays.stream(FstYearSubject.values())
                    .filter(subject -> subject.isExamined())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
        };
        return subjects;
    }

    public static List<? extends YearsSubject> listNonCorrectCodeSubjects(StudyYear studyYear) {
        List<? extends YearsSubject> subjects;
        subjects = switch (studyYear) {
            case FTH_YEAR -> Arrays.stream(FthYearSubject.values())
                    .filter(subject -> subject.isExamined() && !subject.isCodeCorrected())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case TRD_YEAR -> Arrays.stream(TrdYearSubject.values())
                    .filter(subject -> subject.isExamined() && !subject.isCodeCorrected())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            case SND_YEAR -> Arrays.stream(SndYearSubject.values())
                    .filter(subject -> subject.isExamined() && !subject.isCodeCorrected())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
            default -> Arrays.stream(FstYearSubject.values())
                    .filter(subject -> subject.isExamined() && !subject.isCodeCorrected())
                    .sorted(Comparator.comparing(Enum::ordinal)).toList();
        };
        return subjects;
    }


}
