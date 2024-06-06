package ru.game.cat.bot.callback;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryExecutor {

    void executeCallback(Update update);
}
