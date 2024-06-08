package ru.game.cat.bot.command;


import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.factory.BotCommandFactory;

@Component
public abstract class AbstractBotCommand {

    private final BotCommandFactory botCommandFactory;
    private final MessageSender messageSender;

    public AbstractBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender) {
        this.botCommandFactory = botCommandFactory;
        this.messageSender = messageSender;
        this.botCommandFactory.addNewCommand(new BotCommand("/" + getName(), getDescription()),
                "/" + getName(),
                this);
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        var commands = this.botCommandFactory.getBotCommands();
        messageSender.execute(new DeleteMyCommands(new BotCommandScopeDefault(), "ru"));
        messageSender.execute(new DeleteMyCommands(new BotCommandScopeDefault(), "en"));
        if (commands != null && !commands.isEmpty()) {
            messageSender.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), "ru"));
            messageSender.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), "en"));
        }
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract void execute(Update update);
}
