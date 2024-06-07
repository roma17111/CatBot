package ru.game.cat.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.utils.Texts;

import static ru.game.cat.factory.CallbacksFactory.BACK_CALLBACK_TO_CAT_INFO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ButtonsFactory {

    public static InlineKeyboardButton getBackToCatInfoButton() {
        return InlineKeyboardButton.builder()
                .callbackData(BACK_CALLBACK_TO_CAT_INFO)
                .text(Emojy.BACK_EMOJY + " " + Texts.BACK)
                .build();
    }
}
