package ru.game.cat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.CallbackQueryExecutor;
import ru.game.cat.bot.callback.ButtonsFactory;
import ru.game.cat.bot.callback.KeyboardGenerator;
import ru.game.cat.bot.message.MessageSender;

import java.util.ArrayList;
import java.util.List;

@Service("statisticService")
@RequiredArgsConstructor
public class StatisticService implements KeyboardGenerator, CallbackQueryExecutor {

    private final CatService catService;
    private final MessageSender messageSender;

    private String getInfo(Update update) {
        return catService.findActualCat(update).getStatistics().getInfo();
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(ButtonsFactory.getBackToCatInfoButton());
        rows.add(row1);
        markup.setKeyboard(rows);
        return markup;
    }

    @Override
    public void executeCallback(Update update) {
        messageSender.editMessageWithKeyboard(update, getInfo(update), getKeyboard());
    }
}
