package ru.game.cat.bot.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.AbstractCallback;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CallbackQueryProcessor implements Processor {

    private final ApplicationContext applicationContext;

    @Override
    public void process(Update update) {
        String data = update.getCallbackQuery().getData();
        for (AbstractCallback callback : findAll()) {
            if (data.equalsIgnoreCase(callback.getCallback())) {
                callback.execute(update);
            }
        }
    }

    private List<AbstractCallback> findAll() {
        Map<String, AbstractCallback> list = applicationContext.getBeansOfType(AbstractCallback.class);
        return list.values().stream().toList();
    }
}
