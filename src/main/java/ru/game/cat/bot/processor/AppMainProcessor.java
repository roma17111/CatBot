package ru.game.cat.bot.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AppMainProcessor implements Processor {

    private final BotCommandProcessor botCommandProcessor;
    private final LeftChatProcessor leftChatProcessor;
    private final JoinChatProcessor joinChatProcessor;

    @SneakyThrows
    @Override
    public void process(Update update) {
        if (botCommandProcessor.hasCommand(update)) {
            botCommandProcessor.process(update);
        } else if (update.getMessage() != null && update.getMessage().getLeftChatMember() != null) {
            joinChatProcessor.process(update);
            System.out.println("Бот удалён");

        } else if (update.getMessage() != null && update.getMessage().getNewChatMembers() != null) {
            leftChatProcessor.process(update);
            System.out.println("Бот добавлен");
        }

    }
}
