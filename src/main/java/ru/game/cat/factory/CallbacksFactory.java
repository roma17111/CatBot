package ru.game.cat.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallbacksFactory {

    public static final String START_LOOT_EXECUTOR = "START_LOOT_EXECUTOR";
    public static final String TAKE_LOOT_YARD_CALLBACK = "TAKE_LOOT_YARD_CALLBACK";
    public static final String TEN_MINUTES_YARD_CALLBACK = "TEN_MINUTES_YARD_CALLBACK";
    public static final String THIRTY_MINUTES_YARD_CALLBACK = "THIRTY_MINUTES_YARD_CALLBACK";
    public static final String HOUR_YARD_CALLBACK = "HOUR_YARD_CALLBACK";

    public static final String BACK_CALLBACK_TO_CAT_INFO = "BAC_CAT_MAIN_CALLBACK";
    public static final String STATISTICS_CALLBACK = "STATISTICS_CALLBACK";

    //Инвентарь
    public static final String INVENTORY_CALLBACK = "INVENTORY_CALLBACK";
    public static final String RAT_TAIL_CALLBACK = "RAT_TAIL_CALLBACK";
    public static final String MOUSE_PAWS_CALLBACK = "MOUSE_PAWS_CALLBACK";
    public static final String TIN_CAN_CALLBACK = "TIN_CAN_CALLBACK";
    public static final String GET_MILK_BONUS_CALLBACK = "GET_MILK_BONUS_CALLBACK";
}
