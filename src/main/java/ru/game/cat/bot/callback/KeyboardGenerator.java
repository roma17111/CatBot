package ru.game.cat.bot.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardGenerator {
    InlineKeyboardMarkup getKeyboard();
}
