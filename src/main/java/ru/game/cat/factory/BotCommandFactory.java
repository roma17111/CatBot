package ru.game.cat.factory;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.game.cat.bot.command.AbstractBotCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class BotCommandFactory {

    @Value("${bot.enable-start-bot-command-in-burger}")
    private boolean enableStart;

    private final List<BotCommand> botCommands = new ArrayList<>();

    private final Map<String, AbstractBotCommand> mapOfCommands = new HashMap<>();

    public void addNewCommand(@NonNull BotCommand botCommand,
                              @NonNull String commandName,
                              @NonNull AbstractBotCommand command) {

        /*if (!commandName.equalsIgnoreCase("/start")) {
        }*/
        botCommands.add(botCommand);
        mapOfCommands.put(commandName, command);
    }


}
