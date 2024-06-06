package ru.game.cat.service.inventory;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.callback.MainInventoryCallbackQueryExecutor;
import ru.game.cat.bot.config.InventoryConfig;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageFactory;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.exceptions.SatietyException;
import ru.game.cat.service.CatService;
import ru.game.cat.service.InventoryService;
import ru.game.cat.service.StatisticService;
import ru.game.cat.utils.RandomUtils;

import static ru.game.cat.bot.config.InventoryConfig.MAX_ROTATION_INVENTORY;

@Component("tinCan")
@RequiredArgsConstructor
public class TinCan implements MainInventoryCallbackQueryExecutor {

    private final CatService catService;
    private final MessageSender messageSender;
    private final InventoryService inventoryService;
    private final StatisticService statisticService;
    private final InventoryConfig inventoryConfig;
    private final MessageFactory messageFactory;

    @Override
    public void executeMainInventory(@NonNull Update update) {
        try {
            Cat cat = catService.findActualCat(update);
            int h = cat.getStatistics().getHealth();
            if (h <= 5) {
                messageFactory.needToMedicDialog(update);
                return;
            }
            inventoryService.minusTinCan(cat, 1);
            long random = RandomUtils.getRandomNumber(MAX_ROTATION_INVENTORY);
            StringBuilder builder = new StringBuilder();
            if (random > 150 && cat.getStatistics().getHealth() > 0) {
                String health = statisticService.minusHealth(cat, (int) RandomUtils.getRandomNumber(inventoryConfig.getMaxMinusXpBadFood()));
                builder.append(Emojy.HEALTH_EMOJY).append(health).append(" ");
            }
            String satiety = statisticService.plusSatiety(cat, (int) RandomUtils.getRandomNumber(inventoryConfig.getEat()));
            builder.append(Emojy.SATIETY_EMOJY).append(satiety);
            messageSender.sendAlert(update, builder.toString());
            inventoryService.initInventory(cat, update);
        } catch (SatietyException e) {
            messageSender.sendMessageDialog(update, e.getMessage());
        }
    }
}
