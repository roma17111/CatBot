package ru.game.cat.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.command.executors.ToyBotCommandExecutor;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;

@Component
public class ToyBotCommand extends AbstractBotCommand {

    private final ToyBotCommandExecutor botCommandExecutor;

    public ToyBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, ToyBotCommandExecutor botCommandExecutor) {
        super(botCommandFactory, messageSender);
        this.botCommandExecutor = botCommandExecutor;
    }

    @Override
    public String getName() {
        return BotCommands.TOY.getCommand();
    }

    @Override
    public String getDescription() {
        return Emojy.TOY_EMOJY + BotCommands.TOY.getDescription();
    }

    @Override
    public void execute(Update update) {
        botCommandExecutor.executeCommand(update);
    }
}
