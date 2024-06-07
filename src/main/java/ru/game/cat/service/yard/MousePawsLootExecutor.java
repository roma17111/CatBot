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
public class MousePawsLootExecutor implements LootExecutor {

    private final InventoryService inventoryService;

    @Override
    public String getLoot(@NonNull Cat cat, long amount) {
        inventoryService.plusMousePaws(cat, amount);
        return Texts.formatLoot(Inventories.MOUSE_PAW, amount);
    }
}
