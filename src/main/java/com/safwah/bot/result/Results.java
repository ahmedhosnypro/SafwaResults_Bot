package com.safwah.bot.result;

import com.safwah.study.year.YearsSubject;

public class Results {

    public static void beautyPrinter(YearsSubject subject, String score, StringBuilder resultMessage) {
        int intScore = 0;
        int percent = 0;
        int fullScore = 0;
        boolean success = false;
        try {
            var s = score.replaceAll("\\s+", "").split("/");
            intScore = Integer.parseInt(s[0].split("[.]")[0]) * 2;
            fullScore = Integer.parseInt(s[1]) * 2;

            if (intScore > 100) {
                intScore = (int) Math.ceil(intScore / 2.0);
                fullScore = (int) Math.ceil(fullScore / 2.0);
            }

            percent = (intScore * 100) / fullScore;
            success = percent >= 50;
        } catch (Exception ignored) {
            //ignored
        }
        String subjectArabicName = subject.getArabicName();

        int intScoreSpaces = 3 - String.valueOf(intScore).length();
        int percentSpaces = 3 - String.valueOf(percent).length();

        resultMessage.append("</pre>").append(subjectArabicName).append(":\n")
                .append("<pre>").append("\t | ").append(intScore).append(" ".repeat(intScoreSpaces)).append(" | ")
                .append("%").append(percent).append(" ".repeat(percentSpaces)).append(" | ")
                .append(success ? "ناجح" :
                        "راسب")
                .append(" | ").append("\n\n");
    }

    public static int intScore(String score) {
        int intScore = 0;
        try {
            String s = score.replaceAll("\\s+", "").split("/")[0].split("[.]")[0];
            intScore = Integer.parseInt(s);
        } catch (Exception ignored) {
            // ignored
        }
        if (intScore > 50) {
            intScore = (int) Math.ceil(intScore / 2.0);
        }
        return intScore;
    }
}
