package ru.game.cat.bot.callback.click.toy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;
import ru.game.cat.service.PlayToyService;

import static ru.game.cat.factory.CallbacksFactory.PLAY_TOY_CALLBACK;

@Component
@RequiredArgsConstructor
public class PlayToyCallbackQueryExecutor extends AbstractCallback {

    private final PlayToyService playToyService;

    @Override
    public String getCallback() {
        return PLAY_TOY_CALLBACK;
    }

    @Override
    public void execute(Update update) {
        playToyService.playGameCallback(update);
    }
}
