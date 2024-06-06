package ru.game.cat.bot.callback.if_preset;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.CallbackQueryExecutor;
import ru.game.cat.bot.callback.AbstractCallback;

import static ru.game.cat.bot.callback.Callbacks.INVENTORY_CALLBACK;

@Component
public class InventoryCallback extends AbstractCallback {

    private final CallbackQueryExecutor callbackQueryExecutor;

    public InventoryCallback(@Qualifier("inventoryService") CallbackQueryExecutor callbackQueryExecutor) {
        this.callbackQueryExecutor = callbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return INVENTORY_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        callbackQueryExecutor.executeCallback(update);
    }
}
