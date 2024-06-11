package ru.game.cat.bot.command.executors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.annotations.CheckEvents;
import ru.game.cat.service.PlayToyService;

@Component
@RequiredArgsConstructor
public class ToyBotCommandExecutor {

    private final PlayToyService toyService;

    @CheckEvents(checkSleep = true,checkEnergy = true,checkSatiety = true)
    public void executeCommand(@NonNull Update update) {
        toyService.executeCommand(update);
    }
}
