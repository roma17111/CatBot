package ru.game.cat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.game.cat.entity.Yard;

@Getter
@AllArgsConstructor
public enum BotCommands {

    START("start", "Знакомство с ботом"),
    CAT("cat", "Мой котейка"),
    MILK("milk", "Молочко"),
    YARD("yard", "Во двор"),
    SLEEP("sleep", "Сон");

    private final String command;
    private final String description;
}
