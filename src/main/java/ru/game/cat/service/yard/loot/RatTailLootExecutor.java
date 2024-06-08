package ru.game.cat.service.yard.loot;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.Inventories;
import ru.game.cat.service.inventory.InventoryService;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class RatTailLootExecutor implements LootExecutor{

    private final InventoryService inventoryService;
    @Override
    public String getLoot(@NonNull Cat cat, long amount) {
        inventoryService.plusRatTail(cat,amount);
        return Texts.formatLoot(Inventories.RAT_TAIL, amount);
    }
}
