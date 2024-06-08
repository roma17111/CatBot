package ru.game.cat.bot.callback.click.yard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.service.yard.YardExecutor;

import static ru.game.cat.factory.CallbacksFactory.START_LOOT_EXECUTOR;

@Component
@RequiredArgsConstructor
public class StartLootCallbackQueryExecutor extends AbstractCallback {

    private final YardExecutor yardExecutor;

    @Override
    public String getCallback() {
        return START_LOOT_EXECUTOR;
    }

    @Override
    public void execute(Update update) {
        yardExecutor.executeWalk(update, 1);
    }
}
