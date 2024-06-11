package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.repository.ToyRepository;
import ru.game.cat.utils.RandomUtils;

import java.util.List;
import java.util.Random;

import static ru.game.cat.enums.StickerNames.*;

@Service
@RequiredArgsConstructor
public class PlayToyService {

    private final CatService catService;
    private final ToyRepository toyRepository;
    private final StickersService stickersService;

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
}
