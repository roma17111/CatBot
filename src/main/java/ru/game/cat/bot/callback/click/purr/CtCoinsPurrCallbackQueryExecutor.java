package ru.game.cat.bot.callback.click.purr;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.service.PurrService;
import ru.game.cat.service.purr.CatCoinsLootExecutor;

import static ru.game.cat.factory.CallbacksFactory.CAT_COIN_PURR_CALLBACK;

@Component
@RequiredArgsConstructor
public class CtCoinsPurrCallbackQueryExecutor extends AbstractCallback {

    private final PurrService purrService;
    private final CatCoinsLootExecutor catCoinsLootExecutor;

    @Override
    public String getCallback() {
        return CAT_COIN_PURR_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        purrService.executeCallback(update, catCoinsLootExecutor);
    }
}
