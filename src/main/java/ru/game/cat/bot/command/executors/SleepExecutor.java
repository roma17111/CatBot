package ru.game.cat.bot.command.executors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.service.SleepService;

@Component
@RequiredArgsConstructor
public class SleepExecutor {

    private final SleepService sleepService;

    public void executeCommand(@NonNull Update update) {
        sleepService.executeCommand(update);
    }
}
