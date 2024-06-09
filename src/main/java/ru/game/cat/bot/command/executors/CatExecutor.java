package ru.game.cat.bot.command.executors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.service.CatInfoService;


@Component
@RequiredArgsConstructor
public class CatExecutor {

    private final CatInfoService catInfoService;

    public void executeCommand(@NonNull Update update) {
        catInfoService.initInfo(update);
    }
}
