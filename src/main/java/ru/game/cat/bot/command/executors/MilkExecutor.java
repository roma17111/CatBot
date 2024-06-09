package ru.game.cat.bot.command.executors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.service.MilkService;

@Component
@RequiredArgsConstructor
public class MilkExecutor {

    private final MilkService milkService;

    @CheckEvents(checkSleep = true)
    public void executeCommand(@NonNull Update update) {
        milkService.executeCommand(update);
    }
}
