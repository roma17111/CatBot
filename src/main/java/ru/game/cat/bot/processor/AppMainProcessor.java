package ru.game.cat.bot.processor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AppMainProcessor implements Processor {

    private final BotCommandProcessor botCommandProcessor;

    private final CallbackQueryProcessor callbackQueryProcessor;

    @SneakyThrows
    @Override
    public void process(Update update) {
        if (botCommandProcessor.hasCommand(update)) {
            botCommandProcessor.process(update);
        } else if (update.hasCallbackQuery()) {
            callbackQueryProcessor.process(update);
        }

    }
}
