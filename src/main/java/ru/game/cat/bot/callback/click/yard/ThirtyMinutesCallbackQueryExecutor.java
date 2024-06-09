package ru.game.cat.bot.callback.click.yard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.bot.command.executors.YardExecutor;

import static ru.game.cat.factory.CallbacksFactory.THIRTY_MINUTES_YARD_CALLBACK;

@Component
@RequiredArgsConstructor
public class ThirtyMinutesCallbackQueryExecutor extends AbstractCallback {

    private final YardExecutor yardExecutor;

    @Override
    public String getCallback() {
        return THIRTY_MINUTES_YARD_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        yardExecutor.executeWalk(update, 30);
    }
}
