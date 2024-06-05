package ru.game.cat.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackQueryExecutor {

    void executeCallback(Update update);
}
