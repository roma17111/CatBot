package ru.game.cat.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.utils.StickersCatFactory;

@Service
@RequiredArgsConstructor
public class CatInfoService {

    private final CatService catService;
    private final MessageSender messageSender;

    @SneakyThrows
    public void initInfo(Update update) {
        Cat cat = catService.findActualCat(update);
        SendSticker sticker = null;
        if (cat.getSticker() == null) {
            sticker = StickersCatFactory.getMainSticker(update);
        }
        messageSender.execute(sticker);
        messageSender.sendMessage(update.getMessage().getChatId(), cat.getInfo());

    }

}
