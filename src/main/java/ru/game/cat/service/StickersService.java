package ru.game.cat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Sticker;
import ru.game.cat.repository.StickerRepository;

import java.io.File;

@Service
@RequiredArgsConstructor
public class StickersService {

    private final MessageSender messageSender;
    private final StickerRepository stickerRepository;
    private static final String MAIN_STICKER_PATH = "stickers/cat-bomj.webp";


    public void executeSticker(Update update, Sticker sticker) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getChatId());
        sendSticker.setSticker(new InputFile(new File(sticker.getPath())));
        messageSender.executeAsync(sendSticker);
    }

    public Sticker findById(long stickerId) {
        return stickerRepository.findByStickerId(stickerId);
    }


}
