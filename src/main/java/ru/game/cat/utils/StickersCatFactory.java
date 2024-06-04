package ru.game.cat.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

public class StickersCatFactory {

    private static final String MAIN_STICKER_PATH = "stickers/cat-bomj.webp";

    public static SendSticker getMainSticker(Update update) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getChatId());
        sendSticker.setSticker(new InputFile(new File(MAIN_STICKER_PATH)));
        return sendSticker;
    }

    public static SendSticker getMainFromPath(Update update, String path) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getChatId());
        sendSticker.setSticker(new InputFile(new File(path)));
        return sendSticker;
    }

}
