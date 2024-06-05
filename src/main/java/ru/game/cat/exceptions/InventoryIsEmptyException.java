package ru.game.cat.exceptions;

import static ru.game.cat.utils.Texts.INVENTORY_EMPTY_TEXT;

public class InventoryIsEmptyException extends Exception {

    public InventoryIsEmptyException() {
        super(INVENTORY_EMPTY_TEXT);
    }

    public InventoryIsEmptyException(String message) {
        super(message);
    }
}
