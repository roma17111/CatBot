package ru.game.cat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.CallbackQueryExecutor;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Inventory;
import ru.game.cat.exceptions.InventoryIsEmptyException;
import ru.game.cat.utils.InventoryGenerator;
import ru.game.cat.utils.Texts;

@Service("inventoryService")
@RequiredArgsConstructor
public class InventoryService implements CallbackQueryExecutor {

    private final CatService catService;
    private final MessageSender messageSender;

    @Override
    public void executeCallback(Update update) {
        Cat cat = catService.findActualCat(update);
        Inventory inventory = cat.getInventory();
        InventoryGenerator inventoryGenerator = new InventoryGenerator(inventory);
        try {
            var markup = inventoryGenerator.generateKeyboard();
            messageSender.editMessageWithKeyboard(update, Texts.INVENTORY_CAT_TEXT, markup);
        } catch (InventoryIsEmptyException e) {
            messageSender.sendMessageDialog(update, e.getMessage());
        }
    }
}
