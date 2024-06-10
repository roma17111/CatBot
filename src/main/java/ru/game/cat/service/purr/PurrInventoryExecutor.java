package ru.game.cat.service.purr;

import lombok.NonNull;
import ru.game.cat.entity.Cat;

public interface PurrInventoryExecutor {

    String getPurrLoot(@NonNull Cat cat, long amount);
}
