package ru.game.cat.bot.message;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.game.cat.utils.Texts;

@Component
@RequiredArgsConstructor
public class MessageFactory {

    private final MessageSender messageSender;

    public void needToMedicDialog(@NonNull Update update) {
        messageSender.sendMessageDialog(update, Texts.NEED_MEDICINE_TEXT);
    }
}
