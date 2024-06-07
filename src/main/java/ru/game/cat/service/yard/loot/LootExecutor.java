package ru.game.cat.service.yard.loot;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.entity.Cat;

public interface LootExecutor {

    String getLoot(@NonNull Cat cat, long amount);
}
