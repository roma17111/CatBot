package ru.game.cat.bot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.command.AbstractBotCommand;
import ru.game.cat.bot.command.BotCommandFactory;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class BotCommandProcessor implements Processor{

    private static final String PREFIX = "/";
    private final BotCommandFactory botCommandFactory;

    @Override
    public void process(Update update) {
        for (Map.Entry<String, AbstractBotCommand> entry : botCommandFactory.getMapOfCommands().entrySet()) {
            String command = entry.getKey();
            if (update.getMessage().getText().equalsIgnoreCase(command)) {
                entry.getValue().execute(update);
                return;
            }
        }
    }

    public boolean hasCommand(Update update) {
        Message message = update.getMessage();
        return message != null &&
                message.hasText() &&
                message.getText().startsWith(PREFIX);
    }
}
