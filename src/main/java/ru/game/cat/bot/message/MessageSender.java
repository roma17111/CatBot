package ru.game.cat.bot.message;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.HTML;

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
                .parseMode(HTML)
                .text(text)
                .build();
        executeAsync(message);
    }

    @SneakyThrows
    public void deleteMessage(long chatId, long messageId) {
        DeleteMessage message = DeleteMessage.builder()
                .chatId(chatId)
                .messageId((int) messageId)
                .build();
        executeAsync(message);
    }

    @SneakyThrows
    public void sendMessageWithKeyboard(long chatId,
                                        @NonNull String text,
                                        @NonNull ReplyKeyboard keyboard) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .parseMode(HTML)
                .text(text)
                .replyMarkup(keyboard)
                .build();
        executeAsync(message);
    }

    @SneakyThrows
    public void editMessageWithKeyboard(Update update,
                                        @NonNull String text,
                                        @NonNull InlineKeyboardMarkup keyboard) {
        EditMessageText message = EditMessageText.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .parseMode(HTML)
                .text(text)
                .replyMarkup(keyboard)
                .build();
        executeAsync(message);
    }

    @SneakyThrows
    public void editMessage(Update update,
                            @NonNull String text) {
        EditMessageText message = EditMessageText.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .messageId(update.getCallbackQuery().getMessage().getMessageId())
                .parseMode(HTML)
                .replyMarkup(null)
                .text(text)
                .build();
        executeAsync(message);
    }

    public void sendMessageDialog(@NonNull Update update,
                                  @NonNull String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setText(text);
        answerCallbackQuery.setShowAlert(true);
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        try {
            executeAsync(answerCallbackQuery);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAlert(@NonNull Update update,
                          @NonNull String text) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setText(text);
        answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
        try {
            executeAsync(answerCallbackQuery);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAnimation(long chatId, String path) {
        SendAnimation animation = SendAnimation.builder()
                .chatId(chatId)
                .chatId(chatId)
                .animation(new InputFile(new File(path)))
                .build();
        executeAsync(animation);
    }
}
