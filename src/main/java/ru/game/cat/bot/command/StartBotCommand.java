package ru.game.cat.bot.command;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.utils.StickersCatFactory;

import java.io.File;
import java.io.FileInputStream;

@Component
public class StartBotCommand extends AbstractBotCommand {

    private final MessageSender messageSender;

    public StartBotCommand(BotCommandFactory botCommandFactory, MessageSender messageSender, MessageSender messageSender1) {
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

    @SneakyThrows
    @Override
    public void execute(Update update) {
        messageSender.sendMessage(update.getMessage().getChatId(),
                "Добро пожаловать " + update.getMessage().getFrom().getFirstName());
        messageSender.execute(StickersCatFactory.getMainSticker(update));
    }

}
