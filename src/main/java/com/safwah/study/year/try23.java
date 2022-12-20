package com.safwah.study.year;

public class try23 {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i= 20; i<=43; i++){
            stringBuilder.append("C").append(i).append(" = ").append(" '%s'").append(", ");
        }
        System.out.println(stringBuilder+ "\n");

        for (int i=1; i<=25; i++){
            System.out.print("resultSet.getString(" + i + "), ");
        }
    }
}
