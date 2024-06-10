package ru.game.cat.service.purr;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.Inventories;
import ru.game.cat.service.CatService;
import ru.game.cat.service.purr.PurrInventoryExecutor;
import ru.game.cat.service.yard.loot.LootExecutor;
import ru.game.cat.utils.RandomUtils;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class CatCoinsLootExecutor implements LootExecutor, PurrInventoryExecutor {

    private final CatService catService;

    @Override
    public String getLoot(@NonNull Cat cat, long amount) {
        var result = RandomUtils.getRandomNumber(amount * 10);
        cat.setCatCoins(cat.getCatCoins() + result);
        catService.save(cat);
        return Texts.formatLoot(Inventories.CAT_COIN, result);
    }

    @Override
    public String getPurrLoot(@NonNull Cat cat, long amount) {
        return getLoot(cat, amount);
    }
}
