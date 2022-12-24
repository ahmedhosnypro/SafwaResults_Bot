package com.safwah;

public class Student {
    private final String name;
    private final String email;
    private final String code;

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private  int totalScore;

    public int getTotalScore() {
        return totalScore;
    }

    public Student(String email, String name, String code) {
        this.email = email;
        this.name = name;
        this.code = code;
    }

    public Student(String name, String email, String code, int totalScore) {
        this.name = name;
        this.email = email;
        this.code = code;
        this.totalScore = totalScore;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }
}
