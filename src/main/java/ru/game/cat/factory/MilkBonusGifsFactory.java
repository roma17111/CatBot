package ru.game.cat.factory;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.utils.RandomUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MilkBonusGifsFactory {

    private final MessageSender messageSender;

    private static final List<String> stickers = List.of(
            "CgACAgIAAxkDAAICo2ZiuHGL9grfd6zG9gmQj2_MUKRzAAJBSgACb6EZS9i2DrUzat_JNQQ",
            "CgACAgIAAxkDAAICpGZiuHOJ-Tfq0_ZMbDXxtuqAcaZbAAJCSgACb6EZS6HljedrJ9bONQQ",
            "CgACAgIAAxkDAAICpWZiuHQQzQPWL5WhCt5dlVIPp_UkAAJDSgACb6EZSybXe-eb2b4bNQQ",
            "CgACAgIAAxkDAAICpmZiuHX5-yNAD1VDGNAoCVrKLoZvAAJESgACb6EZS02oLGqxE8kQNQQ"
    );

    private String getRandomSticker() {
        long random = RandomUtils.getRandomNumber(0, stickers.size());
        return stickers.get((int) random);
    }

    public void sendCatGif(long chatId) {
        messageSender.sendAnimation(chatId, getRandomSticker());
    }

}
