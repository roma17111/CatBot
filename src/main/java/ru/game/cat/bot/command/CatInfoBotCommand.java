package ru.game.cat.bot.command;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.command.executors.CatExecutor;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.CatInfoService;

import static ru.game.cat.bot.emojy.Emojy.CAT_INFO_EMOJY;

@Component
@Order(10)
public class CatInfoBotCommand extends AbstractBotCommand {

    private final CatExecutor catExecutor;

    public CatInfoBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, CatExecutor catExecutor) {
        super(botCommandFactory, messageSender);
        this.catExecutor = catExecutor;
    }

    @Override
    public String getName() {
        return BotCommands.CAT.getCommand();
    }

    @Override
    public String getDescription() {
        return CAT_INFO_EMOJY + " " + BotCommands.CAT.getDescription();
    }

    @Override
    public void execute(Update update) {
        catExecutor.executeCommand(update);
    }
}
