package ru.game.cat.service.yard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.Inventories;
import ru.game.cat.service.LevelService;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class XPLootExecutor {

    private final LevelService levelService;

    public String getLoot(Update update, Cat cat, long amount) {
        levelService.plusXp(update, cat, amount);
        return Texts.formatLoot(Inventories.XP, amount);
    }
}
