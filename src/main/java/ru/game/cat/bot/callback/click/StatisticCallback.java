package ru.game.cat.bot.callback.click;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.CallbackQueryExecutor;
import ru.game.cat.bot.callback.AbstractCallback;

import static ru.game.cat.factory.CallbacksFactory.STATISTICS_CALLBACK;

@Component
public class StatisticCallback extends AbstractCallback {

    private final CallbackQueryExecutor callbackQueryExecutor;

    public StatisticCallback(@Qualifier("statisticService") CallbackQueryExecutor callbackQueryExecutor) {
        this.callbackQueryExecutor = callbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return STATISTICS_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        callbackQueryExecutor.executeCallback(update);
    }
}
