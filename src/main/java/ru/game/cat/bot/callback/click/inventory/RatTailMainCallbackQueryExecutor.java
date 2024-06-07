package ru.game.cat.bot.callback.click.inventory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.callback.MainInventoryCallbackQueryExecutor;

import static ru.game.cat.factory.CallbacksFactory.RAT_TAIL_CALLBACK;

@Component
public class RatTailMainCallbackQueryExecutor extends AbstractCallback {

    private final MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor;

    public RatTailMainCallbackQueryExecutor(@Qualifier("ratTail") MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor) {
        this.mainInventoryCallbackQueryExecutor = mainInventoryCallbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return RAT_TAIL_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        mainInventoryCallbackQueryExecutor.executeMainInventory(update);
    }
}
