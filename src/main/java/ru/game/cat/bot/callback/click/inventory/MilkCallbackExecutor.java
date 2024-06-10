package ru.game.cat.bot.callback.click.inventory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.callback.MainInventoryCallbackQueryExecutor;

import static ru.game.cat.factory.CallbacksFactory.MILK_CALLBACK_INVENTORY;

@Component
public class MilkCallbackExecutor extends AbstractCallback {

    private final MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor;

    public MilkCallbackExecutor(@Qualifier("milk") MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor) {
        this.mainInventoryCallbackQueryExecutor = mainInventoryCallbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return MILK_CALLBACK_INVENTORY;
    }

    @Override
    public void execute(Update update) {
        mainInventoryCallbackQueryExecutor.executeMainInventory(update);
    }
}
