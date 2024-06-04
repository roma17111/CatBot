package ru.game.cat.bot.callback;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public abstract class AbstractCallback {

    public abstract String getCallback();
    public abstract String getDescription();

    public abstract void execute(Update update);
}
