package ru.game.cat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClockUtil {

    private static final Map<String, String> hours = new HashMap<>();
    private static final Map<String, String> minutes = new HashMap<>();
    private static final Map<String, String> seconds = new HashMap<>();

    static {
        hours.put("1", "час");
        hours.put("2", "часа");
        hours.put("3", "часа");
        hours.put("4", "часа");
        hours.put("5", "часов");
        hours.put("6", "часов");
        hours.put("7", "часов");
        hours.put("8", "часов");
        hours.put("9", "часов");
        hours.put("10", "часов");

        minutes.put("0", "минут");
        minutes.put("1", "минута");
        minutes.put("2", "минуты");
        minutes.put("3", "минуты");
        minutes.put("4", "минуты");
        minutes.put("5", "минут");
        minutes.put("6", "минут");
        minutes.put("7", "минут");
        minutes.put("8", "минут");
        minutes.put("9", "минут");
        minutes.put("10", "минут");
        minutes.put("11", "минут");
        minutes.put("12", "минут");
        minutes.put("13", "минут");
        minutes.put("14", "минут");
        minutes.put("15", "минут");
        minutes.put("16", "минута");
        minutes.put("17", "минут");
        minutes.put("18", "минут");
        minutes.put("19", "минут");
        minutes.put("20", "минут");
        minutes.put("21", "минута");
        minutes.put("22", "минуты");
        minutes.put("23", "минуты");
        minutes.put("24", "минуты");
        minutes.put("25", "минут");
        minutes.put("26", "минут");
        minutes.put("27", "минут");
        minutes.put("28", "минут");
        minutes.put("29", "минут");
        minutes.put("30", "минут");
        minutes.put("31", "минута");
        minutes.put("32", "минуты");
        minutes.put("33", "минуты");
        minutes.put("34", "минуты");
        minutes.put("35", "минут");
        minutes.put("36", "минут");
        minutes.put("37", "минут");
        minutes.put("38", "минут");
        minutes.put("39", "минут");
        minutes.put("40", "минут");
        minutes.put("41", "минута");
        minutes.put("42", "минуты");
        minutes.put("43", "минуты");
        minutes.put("44", "минуты");
        minutes.put("45", "минут");
        minutes.put("46", "минут");
        minutes.put("47", "минут");
        minutes.put("48", "минут");
        minutes.put("49", "минут");
        minutes.put("50", "минут");
        minutes.put("51", "минута");
        minutes.put("52", "минуты");
        minutes.put("53", "минуты");
        minutes.put("54", "минуты");
        minutes.put("55", "минут");
        minutes.put("56", "минут");
        minutes.put("57", "минут");
        minutes.put("58", "минут");
        minutes.put("59", "минут");
        minutes.put("60", "минут");

        seconds.put("0", "секунд");
        seconds.put("1", "секунда");
        seconds.put("2", "секунды");
        seconds.put("3", "секунды");
        seconds.put("4", "секунды");
        seconds.put("5", "секунд");
        seconds.put("6", "секунд");
        seconds.put("7", "секунд");
        seconds.put("8", "секунд");
        seconds.put("9", "секунд");
        seconds.put("10", "секунд");
        seconds.put("11", "секунд");
        seconds.put("12", "секунд");
        seconds.put("13", "секунд");
        seconds.put("14", "секунд");
        seconds.put("15", "секунд");
        seconds.put("16", "секунд");
        seconds.put("17", "секунд");
        seconds.put("18", "секунд");
        seconds.put("19", "секунд");
        seconds.put("20", "секунд");
        seconds.put("21", "секунда");
        seconds.put("22", "секунды");
        seconds.put("23", "секунды");
        seconds.put("24", "секунды");
        seconds.put("25", "секунд");
        seconds.put("26", "секунд");
        seconds.put("27", "секунд");
        seconds.put("28", "секунд");
        seconds.put("29", "секунд");
        seconds.put("30", "секунд");
        seconds.put("31", "секунда");
        seconds.put("32", "секунды");
        seconds.put("33", "секунды");
        seconds.put("34", "секунды");
        seconds.put("35", "секунд");
        seconds.put("36", "секунд");
        seconds.put("37", "секунд");
        seconds.put("38", "секунд");
        seconds.put("39", "секунд");
        seconds.put("40", "секунд");
        seconds.put("41", "секунда");
        seconds.put("42", "секунды");
        seconds.put("43", "секунды");
        seconds.put("44", "секунды");
        seconds.put("45", "секунд");
        seconds.put("46", "секунд");
        seconds.put("47", "секунд");
        seconds.put("48", "секунд");
        seconds.put("49", "секунд");
        seconds.put("50", "секунд");
        seconds.put("51", "секунда");
        seconds.put("52", "секунды");
        seconds.put("53", "секунды");
        seconds.put("54", "секунды");
        seconds.put("55", "секунд");
        seconds.put("56", "секунд");
        seconds.put("57", "секунд");
        seconds.put("58", "секунд");
        seconds.put("59", "секунд");
        seconds.put("60", "секунд");
    }

    public static String validCorrectMinutes(String min) {
        var mins = minutes.get(min);
        return Objects.requireNonNullElse(mins, "мин");
    }

    public static String validateCorrectSeconds(String sec) {
        var secs = seconds.get(sec);
        return Objects.requireNonNullElse(secs, "сек");
    }

    public static String correctHours(String h) {
        var hrs = hours.get(h);
        return Objects.requireNonNullElse(hrs, "ч");
    }

    public static String getHoursMinutesAndSeconds(@NonNull LocalDateTime start,
                                                   @NonNull LocalDateTime end) {
        long hour = Duration.between(start, end).toHours();
        long minute = Duration.between(start, end).toMinutes();
        long seconds = Duration.between(start, end).toSeconds();
        StringBuilder result = new StringBuilder();
        if (hour > 0) {
            result.append(hour).append(" ").append(correctHours(String.valueOf(hour))).append(" ");
            minute = minute % 60;
            seconds = seconds % 60;
        }
        if (minute > 0 && minute < 60) {
            seconds = seconds % 60;
            result.append(minute).append(" ").append(validCorrectMinutes(String.valueOf(minute))).append(" ");
        }
        if (minute < 10 && hour == 0 && seconds < 60) {
            result.append(seconds).append(" ").append(validateCorrectSeconds(String.valueOf(seconds)));
        }
        return result.toString();
    }
}
