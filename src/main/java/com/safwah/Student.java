package com.safwah;

public class Student {
    private final String name;
    private final String email;
    private final String code;

    public Student(String email, String name,  String code) {
        this.email = email;
        this.name = name;
        this.code = code;
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
