package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.CallbackQueryExecutor;
import ru.game.cat.bot.callback.ButtonsFactory;
import ru.game.cat.bot.callback.KeyboardGenerator;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Inventory;
import ru.game.cat.exceptions.InventoryIsEmptyException;
import ru.game.cat.utils.InventoryGenerator;
import ru.game.cat.utils.Texts;

import java.util.ArrayList;
import java.util.List;

@Service("inventoryService")
@RequiredArgsConstructor
public class InventoryService implements CallbackQueryExecutor, KeyboardGenerator {

    private final CatService catService;
    private final MessageSender messageSender;

    @Override
    public void executeCallback(Update update) {
        Cat cat = catService.findActualCat(update);
        initInventory(cat, update);
    }

    public void initInventory(@NonNull Cat cat, @NonNull Update update) {
        Inventory inventory = cat.getInventory();
        InventoryGenerator inventoryGenerator = new InventoryGenerator(inventory);
        try {
            var markup = inventoryGenerator.generateKeyboard();
            messageSender.editMessageWithKeyboard(update, Texts.INVENTORY_CAT_TEXT, markup);
        } catch (InventoryIsEmptyException e) {
            messageSender.editMessageWithKeyboard(update,
                    Emojy.CAT_ERROR_EMOJY + " " + e.getMessage(),
                    getKeyboard());
        }
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(ButtonsFactory.getBackToCatInfoButton());
        rows.add(row1);
        markup.setKeyboard(rows);
        return markup;
    }

}
