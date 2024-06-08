package ru.game.cat.bot.callback.click.sleep;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.service.SleepService;

import static ru.game.cat.factory.CallbacksFactory.SLEEP_STREET_CALLBACK;

@Component
@RequiredArgsConstructor
public class SleepStreetCallbackQueryExecutor extends AbstractCallback {

    private final SleepService sleepService;

    @Override
    public String getCallback() {
        return SLEEP_STREET_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        sleepService.executeCallback(update);
    }
}
