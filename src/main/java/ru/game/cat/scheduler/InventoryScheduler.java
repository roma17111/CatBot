package ru.game.cat.scheduler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Statistics;
import ru.game.cat.service.CatService;
import ru.game.cat.service.StatisticService;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class InventoryScheduler {


    private final StatisticService statisticService;
    private final MessageSender messageSender;
    private final CatService catService;

    @Scheduled(cron  = "${cron.check-satiety}")
    public void checkAllCats() {
        var cats = catService.findAll();
        for (Cat cat : cats) {
            checkCat(cat);
        }
    }

    private void checkCat(@NonNull Cat cat) {
        Statistics statistics = cat.getStatistics();
        int satiety = statistics.getSatiety();
        if (satiety > 0) {
            statisticService.minusSatiety(cat, statistics.getSatietyPerTime());
            if (cat.getStatistics().getSatiety() == 0) {
                messageSender.sendMessage(cat.getChatId(), Emojy.CAT_HUNGRY_EMOJY + " " + Texts.CAT_HUNGRY_TEXT);
            }
        }
    }
}
