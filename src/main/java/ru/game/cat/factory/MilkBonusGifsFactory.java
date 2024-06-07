package ru.game.cat.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.utils.RandomUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MilkBonusGifsFactory {

    private final MessageSender messageSender;

    private static final List<String> stickers = List.of(
            "stickers/milk-bonus/kitty-cat.gif",
            "stickers/milk-bonus/milk-kitty.gif",
            "stickers/milk-bonus/milk-black.gif",
            "stickers/milk-bonus/cats-kittens.gif",
            "stickers/milk-bonus/kitten-spoon.gif"
    );

    private String getRandomSticker() {
        long random = RandomUtils.getRandomNumber(0, stickers.size());
        return stickers.get((int) random);
    }

    public void sendCatGif(long chatId) {
        messageSender.sendAnimation(chatId, getRandomSticker());
    }
}
