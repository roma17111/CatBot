package ru.game.cat.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;

@Component
public class StartBotCommand extends AbstractBotCommand {

    private final MessageSender messageSender;

    public StartBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender,  MessageSender messageSender1) {
        super(botCommandFactory, messageSender);
        this.messageSender = messageSender1;
    }

    @Override
    public String getName() {
        return BotCommands.START.getCommand();
    }

    @Override
    public String getDescription() {
        return BotCommands.START.getDescription();
    }

    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId(),
                "Добро пожаловать " + update.getMessage().getFrom().getFirstName());
    }

}
