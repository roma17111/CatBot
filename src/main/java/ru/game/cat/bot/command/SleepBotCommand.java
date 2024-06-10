package ru.game.cat.bot.command;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.bot.command.executors.SleepExecutor;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.SleepService;

@Component
@Order(40)
public class SleepBotCommand extends AbstractBotCommand {

    private final SleepExecutor sleepExecutor;

    public SleepBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, SleepExecutor sleepExecutor) {
        super(botCommandFactory, messageSender);
        this.sleepExecutor = sleepExecutor;
    }

    @Override
    public String getName() {
        return BotCommands.SLEEP.getCommand();
    }

    @Override
    public String getDescription() {
        return Emojy.ZZZ_EMOJY + " " + BotCommands.SLEEP.getDescription();
    }

    @Override
    public void execute(Update update) {
        sleepExecutor.executeCommand(update);
    }
}
