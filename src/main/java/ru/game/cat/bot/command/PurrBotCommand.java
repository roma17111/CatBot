package ru.game.cat.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.command.executors.PurrExecutor;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;

@Component
public class PurrBotCommand extends AbstractBotCommand {

    private final PurrExecutor purrExecutor;

    public PurrBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, PurrExecutor purrExecutor) {
        super(botCommandFactory, messageSender);
        this.purrExecutor = purrExecutor;
    }

    @Override
    public String getName() {
        return BotCommands.PURR.getCommand();
    }

    @Override
    public String getDescription() {
        return Emojy.PURR_EMOJY + " " + BotCommands.PURR.getDescription();
    }

    @Override
    public void execute(Update update) {
        purrExecutor.executeCommand(update);
    }
}
