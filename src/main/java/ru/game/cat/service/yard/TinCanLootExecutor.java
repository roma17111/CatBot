package ru.game.cat.service.yard;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.Inventories;
import ru.game.cat.service.InventoryService;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class TinCanLootExecutor implements LootExecutor {

    private final InventoryService inventoryService;

    @Override
    public String getLoot(@NonNull Cat cat, long amount) {
        inventoryService.plusTinCan(cat, amount);
        return Texts.formatLoot(Inventories.TIN_CAN, amount);
    }
}
