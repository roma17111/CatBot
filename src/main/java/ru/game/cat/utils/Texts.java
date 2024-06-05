package ru.game.cat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static ru.game.cat.bot.emojy.Emojy.SMILE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Texts {

    public static final String ALREADY_EXISTS_CAT = "У тебя уже есть котофяу" + SMILE;

    public static final String REG_TEXT = """
            Я недавно родился,
            Дней 10 прошло, прозрел...
            
            А мамки почему-то нет😔           \s
            """;

    public static final String INDICATORS = "Показатели";
    public static final String BACK = "Назад";
    public static final String STATISTICS_CAT_TEXT = "Статистика муррэя";
}
