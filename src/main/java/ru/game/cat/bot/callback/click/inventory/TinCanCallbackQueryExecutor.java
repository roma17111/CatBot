package ru.game.cat.bot.callback.click.inventory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.callback.MainInventoryCallbackQueryExecutor;

import static ru.game.cat.bot.callback.Callbacks.TIN_CAN_CALLBACK;

@Component
public class TinCanCallbackQueryExecutor extends AbstractCallback {

    private final MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor;

    public TinCanCallbackQueryExecutor(@Qualifier("tinCan") MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor) {
        this.mainInventoryCallbackQueryExecutor = mainInventoryCallbackQueryExecutor;
    }


    @Override
    public String getCallback() {
        return TIN_CAN_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        mainInventoryCallbackQueryExecutor.executeMainInventory(update);
    }
}
