package ru.game.cat.bot.command;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.factory.BotCommandFactory;
import ru.game.cat.service.yard.YardExecutor;

import static ru.game.cat.bot.emojy.Emojy.EMOJY_TREE_ONE;

@Component
@Order(3)
public class YardBotCommand extends AbstractBotCommand {

    private final YardExecutor yardExecutor;

    public YardBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, YardExecutor yardExecutor) {
        super(botCommandFactory, messageSender);
        this.yardExecutor = yardExecutor;
    }

    @Override
    public String getName() {
        return BotCommands.YARD.getCommand();
    }

    @Override
    public String getDescription() {
        return EMOJY_TREE_ONE + " " +BotCommands.YARD.getDescription();
    }

    @Override
    public void execute(Update update) {
        yardExecutor.executeCommand(update);
    }
}
