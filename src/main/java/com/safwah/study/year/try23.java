package com.safwah.study.year;

public class try23 {
    public static void main(String[] args) {
        String name1 = "مریم".replaceAll("ی", "ي");
        String name2 =
                "مريم";

        for (int i = 0; i < name1.length(); i++) {
            System.out.println(String.format(name1.charAt(i) + " " + "\\u%04x", (int) name1.charAt(i)));
            System.out.println(String.format(name2.charAt(i) + " " + "\\u%04x", (int) name2.charAt(i)));

        }


        System.out.println(name1.equals(name2));
    }
}
