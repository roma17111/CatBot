package ru.game.cat.bot.command;

import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.bot.command.executors.MilkExecutor;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.MilkService;

@Component
@Order(30)
public class MilkBonusCommand extends AbstractBotCommand {

    private final MilkExecutor milkExecutor;

    public MilkBonusCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, MilkExecutor milkExecutor) {
        super(botCommandFactory, messageSender);
        this.milkExecutor = milkExecutor;
    }

    @Override
    public String getName() {
        return BotCommands.MILK.getCommand();
    }

    @Override
    public String getDescription() {
        return Emojy.MILK_EMOJY + BotCommands.MILK.getDescription();
    }

    @Override
    public void execute(Update update) {
        milkExecutor.executeCommand(update);
    }
}
