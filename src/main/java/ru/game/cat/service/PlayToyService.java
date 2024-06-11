package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Toy;
import ru.game.cat.enums.BotCommands;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.repository.ToyRepository;
import ru.game.cat.utils.RandomUtils;

import java.util.List;
import java.util.Random;

import static ru.game.cat.enums.StickerNames.*;
import static ru.game.cat.utils.Texts.TOY_NOT_FOUND_TEXT;

@Service
@RequiredArgsConstructor
public class PlayToyService {

    private final CatService catService;
    private final ToyRepository toyRepository;
    private final StickersService stickersService;
    private final MessageSender messageSender;

    private static final List<String> WAYS_TO_GET_TOY = List.of(
            "/yard - Прогуляться по двору"
    );

    private void initToyNotFoundMessage(@NonNull Update update) {
        StringBuilder ways = new StringBuilder(TOY_NOT_FOUND_TEXT);
        ways.append("Способы получения:\n");
        for (int i = 0; i < WAYS_TO_GET_TOY.size(); i++) {
            ways.append(String.format("%d. %s", i + 1, WAYS_TO_GET_TOY.get(i))).append("\n");
        }
        messageSender.sendMessage(update.getMessage().getChatId(), ways.toString());
    }

    private static final List<StickerNames> stickers = List.of(
            PLAY_TOY_ONE,
            PLAY_TOY_TWO
    );

    private void initMainToySticker(@NonNull Update update) {
        stickersService.executeSticker(update, stickersService.findById(MAIN_TOY_STICKER));
    }

    private void initFinishSticker(@NonNull Update update) {
        long random = RandomUtils.getRandomNumber(0, stickers.size());
        StickerNames stickerNames = stickers.get((int) random);
        stickersService.executeSticker(update, stickersService.findById(stickerNames));
    }

    public void initMessageForInventory(@NonNull Update update) {
        messageSender.sendMessageDialog(update,
                Emojy.CAT+Emojy.TOY_EMOJY+"Поиграть с игрушкой можно прописав команду /"+ BotCommands.TOY.getCommand());
    }
}
