package ru.game.cat.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.callback.ButtonsFactory;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.exceptions.InventoryIsEmptyException;
import ru.game.cat.entity.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.game.cat.bot.callback.Callbacks.*;

public class InventoryGenerator {

    private final Inventory inventory;
    private final int buttonsInRow = 2;

    private final List<InlineKeyboardButton> listButtons = new ArrayList<>();

    public InventoryGenerator(Inventory inventory) {
        this.inventory = inventory;
        initCounter();
    }

    private void initCounter() {
        if (inventory.getMousePaws() > 0) {
            listButtons.add(InlineKeyboardButton.builder()
                    .callbackData(MOUSE_PAWS_CALLBACK)
                    .text(Emojy.MOUSE_PAWS + inventory.getMousePaws())
                    .build());
        }
        if (inventory.getRatTail() > 0) {
            listButtons.add(InlineKeyboardButton.builder()
                    .callbackData(RAT_TAIL_CALLBACK)
                    .text(Emojy.RAT_TAIL + inventory.getRatTail())
                    .build());
        }
        if (inventory.getTinCan() > 0) {
            listButtons.add(InlineKeyboardButton.builder()
                    .callbackData(TIN_CAN_CALLBACK)
                    .text(Emojy.TIN_CAN + inventory.getTinCan())
                    .build());
        }
    }

    public InlineKeyboardMarkup generateKeyboard() throws InventoryIsEmptyException {
        if (listButtons.isEmpty()) {
            throw new InventoryIsEmptyException();
        }
        long countRows = listButtons.size() / buttonsInRow + 1;

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < countRows; i++) {
            rows.add(new ArrayList<>());
        }
        int count = 0;
        for (int i = 0; i < listButtons.size(); i++) {
            var row = rows.get(count);
            row.add(listButtons.get(i));
            if ((i + 1) % buttonsInRow == 0) {
                count++;
            }
        }
        rows.add(new ArrayList<>(List.of(ButtonsFactory.getBackToCatInfoButton())));
        markup.setKeyboard(rows);
        return markup;
    }

}
