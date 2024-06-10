package ru.game.cat.bot.command.executors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.service.PurrService;

@Component
@RequiredArgsConstructor
public class PurrExecutor {

    private final PurrService purrService;

    @CheckEvents(checkSleep = true,checkYard = true)
    public void executeCommand(@NonNull Update update) {
        purrService.executeCommand(update);
    }

}
