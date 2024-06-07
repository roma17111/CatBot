package ru.game.cat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    private static final Random random = new SecureRandom();
    private static final int ONE = 1;

    public static long getRandomNumber(long max) {
        return getRandomNumber(ONE, max);
    }

    public static long getRandomNumber(long min, long max) {
        return random.nextLong(min, max);
    }
}
