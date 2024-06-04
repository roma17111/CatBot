package ru.game.cat.bot.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BotCommands {

    START("start", "Знакомство с ботом");

    private final String command;
    private final String description;
}
