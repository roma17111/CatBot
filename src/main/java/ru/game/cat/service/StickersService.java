package ru.game.cat.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Sticker;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.repository.StickerRepository;

import java.io.File;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StickersService {

    private final MessageSender messageSender;
    private final StickerRepository stickerRepository;
    private static final String MAIN_STICKER_PATH = "stickers/cat-bomj.webp";

    @SneakyThrows
    public void executeSticker(Update update, Sticker sticker) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getChatId());
        if (sticker.getFileId() == null) {
            sendSticker.setSticker(new InputFile(new File(sticker.getPath())));
            var result = messageSender.executeAsync(sendSticker);
            String fileId = result.get().getSticker().getFileId();
            sticker.setFileId(fileId);
            save(sticker);
        } else {
            sendSticker.setSticker(new InputFile(sticker.getFileId()));
            messageSender.executeAsync(sendSticker);
        }


    }

    public void executeSticker(Update update, String fileId) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(update.getMessage().getChatId());
        var file = new InputFile(fileId);
        sendSticker.setSticker(file);
        messageSender.executeAsync(sendSticker);

    }

    public Sticker findById(StickerNames stickerId) {
        return stickerRepository.findByStickerId(stickerId);
    }

    public void save(Sticker sticker) {
        stickerRepository.save(sticker);
    }

}
