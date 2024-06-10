package ru.game.cat.service.inventory;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.MainInventoryCallbackQueryExecutor;
import ru.game.cat.bot.config.InventoryConfig;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.exceptions.HealthyException;
import ru.game.cat.exceptions.SatietyException;
import ru.game.cat.service.CatService;
import ru.game.cat.service.StatisticService;

@Component("milk")
@RequiredArgsConstructor
public class Milk implements MainInventoryCallbackQueryExecutor {

    private final InventoryService inventoryService;
    private final MessageSender messageSender;;
    private final StatisticService statisticService;
    private final CatService catService;


    @Override
    public void executeMainInventory(@NonNull Update update) {
        try {
            Cat cat = catService.findActualCat(update);
            inventoryService.minusMilk(cat, 1);
            StringBuilder builder = new StringBuilder();
            String health = null;
            try {
                health = statisticService.plusHealth(cat, 20);
            } catch (HealthyException ignored) {
            }
            if (health != null && !health.isEmpty()) {
                builder.append(Emojy.HEALTH_EMOJY).append(health).append(" ");
            }
            String satiety = statisticService.plusSatiety(cat, (int) 50);
            builder.append(Emojy.SATIETY_EMOJY).append(satiety);
            messageSender.sendAlert(update, builder.toString());
            inventoryService.initInventory(cat, update);
        } catch (SatietyException e) {
            messageSender.sendMessageDialog(update, e.getMessage());
        }
    }
}
