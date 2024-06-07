package ru.game.cat.bot.callback.click;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.callback.CallbackQueryExecutor;

import static ru.game.cat.bot.callback.Callbacks.GET_MILK_BONUS_CALLBACK;

@Component
public class MilkBonusCallbackQueryExecutor extends AbstractCallback {

    private final CallbackQueryExecutor callbackQueryExecutor;

    public MilkBonusCallbackQueryExecutor(@Qualifier("milkService") CallbackQueryExecutor callbackQueryExecutor) {
        this.callbackQueryExecutor = callbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return GET_MILK_BONUS_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        callbackQueryExecutor.executeCallback(update);
    }
}
