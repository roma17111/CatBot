package ru.game.cat.service.yard;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Yard;
import ru.game.cat.factory.YardLootFactory;
import ru.game.cat.service.CatService;
import ru.game.cat.service.yard.loot.LootExecutor;
import ru.game.cat.service.yard.loot.XPLootExecutor;
import ru.game.cat.utils.RandomUtils;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@Service
@RequiredArgsConstructor
public class YardService {

    private final CatService catService;
    private final YardLootFactory lootFactory;
    private final XPLootExecutor xpLootExecutor;

    @Value("${bot.yard.start-max-xp}")
    private int maxXp;

    @Value("${bot.yard.start-max-loot}")
    private int maxLoot;

    public Yard getActualYard(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        return getActualYard(cat);
    }

    public Yard getActualYard(@NonNull Cat cat) {

        Yard yard = cat.getYard();
        if (yard == null) {
            yard = Yard.builder()
                    .checkDate(LocalDateTime.now().minusYears(100))
                    .maxLoot(maxLoot)
                    .maxXp(maxXp)
                    .currentWalkMinutes(ZERO)
                    .totalWalkMinutes(ZERO)
                    .isInTheWalk(false)
                    .isMeetAdventure(false)
                    .build();
            cat.setYard(yard);
            catService.save(cat);
        }
        return yard;
    }

    public void walk(@NonNull Cat cat,
                      int minutes) {
        Yard yard = cat.getYard();
        yard.setCheckDate(LocalDateTime.now().plusMinutes(minutes));
        yard.setInTheWalk(true);
        yard.setCurrentWalkMinutes(minutes);
        yard.setTotalWalkMinutes(yard.getTotalWalkMinutes() + minutes);
        cat.setYard(yard);
        catService.save(cat);
    }

    public void finishWalk(@NonNull Cat cat) {
        Yard yard = cat.getYard();
        yard.setInTheWalk(false);
        yard.setCurrentWalkMinutes(0);
        cat.setYard(yard);
        catService.save(cat);
    }

    public String getLoot(@NonNull Cat cat, @NonNull Update update) {
        int iterations = 0;
        long minutes = getActualYard(cat).getCurrentWalkMinutes();
        if (minutes <= 10) {
            iterations = 1;
        } else if (minutes == 30) {
            iterations = 2;
        } else if (minutes == 60) {
            iterations = 3;
        }
        StringBuilder result = new StringBuilder();
        result.append(xpLootExecutor.getLoot(update, cat, RandomUtils.getRandomNumber(getActualYard(cat).getMaxXp())))
                .append("\n");
        var loots = lootFactory.getAllObjects();
        for (int i = 0; i < iterations; i++) {
            long randomLoot = RandomUtils.getRandomNumber(0, loots.size());
            var loot = loots.get((int) randomLoot);
            String s = getRandomLootPosition(loot, cat);
            loots.remove(loot);
            if (!s.isEmpty()) {
                result.append(s).append("\n");
            }
        }
        return result.toString();
    }

    private String getRandomLootPosition(@NonNull LootExecutor lootExecutor,
                                         @NonNull Cat cat) {
        long r = RandomUtils.getRandomNumber(200);
        String s = "";
        if (r >= 80) {
            long randomLoots = getActualYard(cat).getMaxLoot();
            s = lootExecutor.getLoot(cat, RandomUtils.getRandomNumber(randomLoots + 1));
        }
        return s;
    }

    public boolean walkIsNotFinished(@NonNull Cat cat) {
        var yard = cat.getYard();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = yard.getCheckDate();

        long minutes = Duration.between(start, end).toMinutes();
        if (minutes <= 0L) {
            return false;
        }
        return minutes < yard.getCurrentWalkMinutes();
    }


}
