package ru.game.cat.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.w3c.dom.Text;
import ru.game.cat.bot.CallbackQueryExecutor;
import ru.game.cat.bot.callback.Callbacks;
import ru.game.cat.bot.callback.KeyboardGenerator;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.utils.Texts;

import java.util.ArrayList;
import java.util.List;

import static ru.game.cat.utils.Texts.STATISTICS_CAT_TEXT;

@Service("catInfoService")
@RequiredArgsConstructor
public class CatInfoService implements KeyboardGenerator, CallbackQueryExecutor {

    private final CatService catService;
    private final MessageSender messageSender;
    private final StickersService stickersService;

    @SneakyThrows
    public void initInfo(Update update) {
        Cat cat = catService.findActualCat(update);
        stickersService.executeSticker(update, cat.getSticker());
        messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(),
                cat.getInfo(),
                getKeyboard());
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton statistics = new InlineKeyboardButton();
        statistics.setCallbackData(Callbacks.STATISTICS_CALLBACK);
        statistics.setText(Emojy.EMOJI_STATISTIC + " " + STATISTICS_CAT_TEXT);

        InlineKeyboardButton inventory = new InlineKeyboardButton();
        inventory.setCallbackData(Callbacks.INVENTORY_CALLBACK);
        inventory.setText(Emojy.CAT_THINGS_EMOJY + " " + Texts.INVENTORY_CAT_TEXT);
        row1.add(statistics);
        rows.add(row1);
        rows.add(row2);
        markup.setKeyboard(rows);
        return markup;
    }

    @Override
    public void executeCallback(Update update) {
        Cat cat = catService.findActualCat(update);
        messageSender.editMessageWithKeyboard(update,
                cat.getInfo(),
                getKeyboard());
    }
}
