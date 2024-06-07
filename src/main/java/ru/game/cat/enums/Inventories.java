package ru.game.cat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.game.cat.bot.emojy.Emojy;

@Getter
@AllArgsConstructor
public enum Inventories {

    MOUSE_PAW(Emojy.MOUSE_PAWS, "Мышиная лапка"),
    RAT_TAIL(Emojy.RAT_TAIL, "Крысиный хвостик"),
    TIN_CAN(Emojy.TIN_CAN, "Консервная банка");

    private final String emoji;
    private final String name;
}
