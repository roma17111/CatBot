package ru.game.cat.bot.message;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class MessageSender extends DefaultAbsSender {
    protected MessageSender(DefaultBotOptions options,
                            @Value("${bot.token}") String botToken) {
        super(options, botToken);
    }

    @SneakyThrows
    public void sendMessage(long chatId, @NonNull String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        executeAsync(message);
    }
}
