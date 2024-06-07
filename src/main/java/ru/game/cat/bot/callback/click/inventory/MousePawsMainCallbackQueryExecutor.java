package ru.game.cat.bot.callback.click.inventory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.callback.MainInventoryCallbackQueryExecutor;

import static ru.game.cat.factory.CallbacksFactory.MOUSE_PAWS_CALLBACK;

@Component
public class MousePawsMainCallbackQueryExecutor extends AbstractCallback {

    private final MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor;

    public MousePawsMainCallbackQueryExecutor(@Qualifier("mousePaws") MainInventoryCallbackQueryExecutor mainInventoryCallbackQueryExecutor) {
        this.mainInventoryCallbackQueryExecutor = mainInventoryCallbackQueryExecutor;
    }

    @Override
    public String getCallback() {
        return MOUSE_PAWS_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        mainInventoryCallbackQueryExecutor.executeMainInventory(update);
    }
}
