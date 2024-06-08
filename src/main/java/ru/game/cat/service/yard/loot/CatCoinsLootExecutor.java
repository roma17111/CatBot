package ru.game.cat.service.yard.loot;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.game.cat.entity.Cat;
import ru.game.cat.enums.Inventories;
import ru.game.cat.service.CatService;
import ru.game.cat.utils.RandomUtils;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class CatCoinsLootExecutor implements LootExecutor {

    private final CatService catService;

    @Override
    public String getLoot(@NonNull Cat cat, long amount) {
        cat.setCatCoins(cat.getCatCoins() + amount);
        catService.save(cat);
        return Texts.formatLoot(Inventories.CAT_COIN, RandomUtils.getRandomNumber(amount * 10));
    }
}
