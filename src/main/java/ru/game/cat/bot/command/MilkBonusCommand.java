package ru.game.cat.bot.command;

import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.service.MilkService;

@Component
@Order(2)
public class MilkBonusCommand extends AbstractBotCommand {

    private final MilkService milkService;

    public MilkBonusCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, MilkService milkService) {
        super(botCommandFactory, messageSender);
        this.milkService = milkService;
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
        milkService.initForCommand(update);
    }
}
