package ru.game.cat.bot.command;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.SleepService;

@Component
@Order(40)
public class SleepBotCommand extends AbstractBotCommand {

    private final SleepService sleepService;

    public SleepBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, SleepService sleepService) {
        super(botCommandFactory, messageSender);
        this.sleepService = sleepService;
    }

    @Override
    public String getName() {
        return BotCommands.SLEEP.getCommand();
    }

    @Override
    public String getDescription() {
        return Emojy.BED_EMOJY + " " + BotCommands.SLEEP.getDescription();
    }

    @Override
    public void execute(Update update) {
        sleepService.executeCommand(update);
    }
}
