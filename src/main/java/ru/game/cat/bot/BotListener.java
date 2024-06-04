package ru.game.cat.bot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import ru.game.cat.bot.config.BotConfig;
import ru.game.cat.bot.processor.AppMainProcessor;

@Component
@RequiredArgsConstructor
public class BotListener implements LongPollingBot {

    private final DefaultBotOptions botOptions;
    private final BotConfig config;
    private final AppMainProcessor mainProcessor;

    @PostConstruct
    public void init() {
        config.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        this.mainProcessor.process(update);
    }

    @Override
    public BotOptions getOptions() {
        return this.botOptions;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {

    }

    @Override
    public String getBotUsername() {
        return this.config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return this.config.getBotToken();
    }
}
