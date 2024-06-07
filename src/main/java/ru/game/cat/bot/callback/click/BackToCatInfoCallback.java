package ru.game.cat.bot.callback.click;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.CallbackQueryExecutor;
import ru.game.cat.bot.callback.AbstractCallback;

import static ru.game.cat.factory.CallbacksFactory.BACK_CALLBACK_TO_CAT_INFO;

@Component("backToCatInfoCallback")
public class BackToCatInfoCallback extends AbstractCallback {

    private final CallbackQueryExecutor callbackQueryExecutor;

    public BackToCatInfoCallback(@Qualifier("catInfoService") CallbackQueryExecutor callbackQueryExecutor) {
        this.callbackQueryExecutor = callbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return BACK_CALLBACK_TO_CAT_INFO;
    }

    @Override
    public void execute(Update update) {
        callbackQueryExecutor.executeCallback(update);
    }
}
