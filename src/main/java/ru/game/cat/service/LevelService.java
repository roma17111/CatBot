package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.entity.Cat;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final CatService catService;

    public String plusXp(@NonNull Update update,
                         @NonNull Cat cat,
                         long xp) {
        cat.setXp(cat.getXp()+xp);
        cat.setXpFromLevel(cat.getXpFromLevel()+xp);
        checkLevel(update,cat);
        return String.format("%s <b>Опыт + %d</b>", Emojy.LEVEL, xp);
    }

    private void checkLevel(@NonNull Update update, @NonNull Cat cat) {
        long xp = cat.getXpFromLevel();
        long maxXp = cat.getNecessaryXpForUp();
        if (xp >= maxXp) {
            xp = maxXp - xp;
            maxXp = setMaxXp(maxXp);
            cat.setLevel(cat.getLevel() + 1);
            cat.setXpFromLevel(xp);
            cat.setNecessaryXpForUp(maxXp);
            levelInfo(update, cat);
        }
    }

    private long setMaxXp(long maxXp) {
        long result = maxXp / 100 * 20;
        return maxXp + result;
    }

    private void levelInfo(@NonNull Update update, @NonNull Cat cat) {
        //дописать
    }
}
