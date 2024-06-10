package ru.game.cat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.enums.Inventories;

import static ru.game.cat.bot.emojy.Emojy.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Texts {

    public static final String ALREADY_EXISTS_CAT = "У тебя уже есть котофяу" + SMILE;

    public static final String REG_TEXT = """
            Я недавно родился,
            Дней 10 прошло, прозрел...
                        
            А мамки почему-то нет😔           \s
            """;

    public static final String INVENTORY_EMPTY_TEXT = "У вас нет ни одной мурр-штуки";
    public static final String INDICATORS = "Показатели";
    public static final String BACK = "Назад";
    public static final String STATISTICS_CAT_TEXT = "Статистика муррэя";
    public static final String INVENTORY_CAT_TEXT = "Мурр-штуки";
    public static final String HEALTHY_TEXT = "Важ котофей полностью здоров";
    public static final String SATIETY_TEXT = "Важ мурр-муррей не голоден";

    public static final String NO_RAT_TAIL_TEXT = "У кошары недостаточно крысиных хвостиков";
    public static final String NO_MOUSE_PAWS_TEXT = "У кошары недостаточно мышиных лапок";
    public static final String NO_TIN_CAN_TEXT = "У кошары недостаточно консервных банок";

    public static final String NEED_MEDICINE_TEXT = Emojy.MEDICINE_ERROR_EMOJY + " Дружище, " +
            "кажется ты сильно расклеился, быстро встать на лапы тебе поможет " + Emojy.MILK_EMOJY +
            " плашка молока" + SMILE;

    public static final String CAT_HUNGRY_TEXT = """
            Котик сильно проголодался
            """;
    public static final String MILK_INFO_TEXT = """
            Центр кормления молочком для <b>котят</b>
            """;
    public static final String DRINK_MILK_TEXT = "Вылакать молочко";
    public static final String YOU_GOT_MILK = String.format("""
            %s Ты уже получил %s молочко\s
            %s котик
            """, Emojy.CAT_ERROR_EMOJY, Emojy.MILK_EMOJY, Emojy.CAT);

    public static final String YARD_INFO_TEXT = String.format(
            """
                    %s
                    Удачной прогулки, %s<b>красавчик</b>
                    Возможно тебе сегодня повезёт и ты найдёшь что-нибудь
                    интересное во дворе. %s%s%s
                                
                    %s<b>Выберите время прогулки</b>
                    """,
            GOOD_LUCK_YARD_EMOJY,
            LOVE_CAT,
            EMOJY_TREE_ONE,
            SUNNY_EMOJY,
            YARD_TREE_EMOJY,
            CAT_STEPS
    );

    public static final String START_LOOT_CAT_TEXT = String.format("""
            %s А не разведать ли обстановочку %s %s
            """, CAT, QUESTION_EMOJY, THINK_EMOJY);

    public static final String START_LOOT_BUTTON_TEXT = DOOR_EMOJY + " Во двор";


    public static final String FINISH_WALK_IN_YARD_TEXT = CAT + " Ваш друг вернулся с прогулки. " + CLICK_UNDER_EMOJY;
    public static final String TAKE_LOOT_TEXT = GET_LOOT_EMOJY + " Забрать находки";

    public static final String CAT_TYRED_SLEEP_TEXT = TYRED_EMOJY + """
            Котичка сильно устал и хочет отдохнуть.        
            """;
    public static final String CAT_IS_SLEEP_TEXT = BED_EMOJY + " Кошара спит, но скоро обещал проснуться" + SMILE + "\n";

    public static final String SLEEP_STREET_INFO_TEXT = Emojy.STREET_EMOJY + " Дружище, котёнок, ты можешь поспать " +
            "на улице. Выбери себе подходящее место" + SMILE;
    public static final String SLEEP_STREET_BUTTON_TEXT = BED_EMOJY + " Поспать";
    public static final String CAT_CANT_SLEEP_TEXT = CAT_ERROR_EMOJY + " Котофяу не хочет спать. Он полон сил" + SMILE;

    public static final String CAT_WALK_IN_YARD_TEXT = Emojy.CAT_ERROR_EMOJY + " Котик шляется по двору \n Посмотрите, не " +
            "принёс ли он чего-нибудь \n\n" +
            "/yard";

    public static final String MURR_QUESTION = CAT + "Что хотите получить" + QUESTION_EMOJY;

    public static final String MURR_GET_TEXT = CAT + " Мурр, посмотри, что ты получил" + SMILE;

    public static String formatLoot(Inventories inventories, long amount) {
        return String.format("%s %s<b>%s</b> + %d",
                Emojy.DOT_EMOJY,
                inventories.getEmoji(),
                inventories.getName(),
                amount);
    }
}
