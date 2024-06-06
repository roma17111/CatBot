package ru.game.cat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public static String validateCorrectSeconds(String sec) {
        if (sec.trim().endsWith("0") ||
                sec.trim().endsWith("5") ||
                sec.trim().endsWith("6") ||
                sec.trim().endsWith("7") ||
                sec.trim().endsWith("8") ||
                sec.trim().endsWith("9") ||
                sec.trim().endsWith("11") ||
                sec.trim().endsWith("12") ||
                sec.trim().endsWith("13") ||
                sec.trim().endsWith("14")) {
            return "секунд";
        }
        if (sec.trim().endsWith("2") ||
                sec.trim().endsWith("3") ||
                sec.trim().endsWith("4")) {
            return "секунды";
        }
        if (sec.trim().endsWith("1")) {
            return "секунду";
        } else {
            return "";
        }
    }

    public static String correctHours(String hours) {
        if (hours.trim().endsWith("0") ||
                hours.trim().endsWith("5") ||
                hours.trim().endsWith("6") ||
                hours.trim().endsWith("7") ||
                hours.trim().endsWith("8") ||
                hours.trim().endsWith("9") ||
                hours.trim().endsWith("11") ||
                hours.trim().endsWith("12") ||
                hours.trim().endsWith("13") ||
                hours.trim().endsWith("14")) {
            return "часов";
        }
        if (hours.trim().endsWith("2") ||
                hours.trim().endsWith("3") ||
                hours.trim().endsWith("4")) {
            return "часа";
        }
        if (hours.trim().endsWith("1")) {
            return "час";
        } else {
            return "";
        }
    }

    public static String getHoursMinutesAndSeconds(@NonNull LocalDateTime start,
                                                   @NonNull LocalDateTime end) {
        long hour = Duration.between(start, end).toHours();
        long minute = Duration.between(start, end).toMinutes();
        long seconds = Duration.between(start, end).toSeconds();
        StringBuilder result = new StringBuilder();

        if (hour > 0) {
            result.append(hour).append(" ").append(correctHours(String.valueOf(hour)));
        }
        if (minute > 0 && minute < 60) {
            result.append(minute).append(" ").append(validCorrectMinutes(String.valueOf(hour)));
        }
        if (seconds < 60) {
            result.append(seconds).append(" ").append(validateCorrectSeconds(String.valueOf(seconds)));
        }
        return result.toString();
    }
}
