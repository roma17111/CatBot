package ru.game.cat.bot.callback.click.yard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.service.yard.YardExecutor;
import ru.game.cat.service.yard.YardService;

import static ru.game.cat.factory.CallbacksFactory.TEN_MINUTES_YARD_CALLBACK;

@Component
@RequiredArgsConstructor
public class TenMinutesCallbackQueryExecutor extends AbstractCallback {

    private final YardExecutor yardExecutor;

    @Override
    public String getCallback() {
        return TEN_MINUTES_YARD_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        yardExecutor.executeWalk(update, 10);
    }
}
