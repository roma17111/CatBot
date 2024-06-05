package ru.game.cat.bot.callback.if_preset;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.CallbackQueryExecutor;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.service.CatInfoService;

import static ru.game.cat.bot.callback.Callbacks.BACK_CALLBACK_TO_CAT_INFO;

@Component
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
