package ru.game.cat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClockUtil {

    public static String validCorrectMinutes(String minutes) {
        if (minutes.trim().endsWith("0") ||
                minutes.trim().endsWith("5") ||
                minutes.trim().endsWith("6") ||
                minutes.trim().endsWith("7") ||
                minutes.trim().endsWith("8") ||
                minutes.trim().endsWith("9") ||
                minutes.trim().endsWith("11") ||
                minutes.trim().endsWith("12") ||
                minutes.trim().endsWith("13") ||
                minutes.trim().endsWith("14")) {
            return "минут";
        }
        if (minutes.trim().endsWith("2") ||
                minutes.trim().endsWith("3") ||
                minutes.trim().endsWith("4")) {
            return "минуты";
        }
        if (minutes.trim().endsWith("1")) {
            return "минуту";
        } else {
            return "";
        }
    }
}
