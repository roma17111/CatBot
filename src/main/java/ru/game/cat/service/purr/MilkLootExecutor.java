package ru.game.cat.service.purr;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.Inventories;
import ru.game.cat.service.inventory.InventoryService;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class MilkLootExecutor implements PurrInventoryExecutor{

    private final InventoryService inventoryService;

    @Override
    public String getPurrLoot(@NonNull Cat cat, long amount) {
        inventoryService.plusMilk(cat, amount);
        return Texts.formatLoot(Inventories.MILK, amount);
    }
}
