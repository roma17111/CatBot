package ru.game.cat.bot.callback.click.inventory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.service.PlayToyService;

import static ru.game.cat.factory.CallbacksFactory.INVENTORY_MAIN_TOY_CALLBACK;

@Component
@RequiredArgsConstructor
public class ToyInventoryExecutor extends AbstractCallback {

    private final PlayToyService toyService;

    @Override
    public String getCallback() {
        return INVENTORY_MAIN_TOY_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        toyService.initMessageForInventory(update);
    }
}
