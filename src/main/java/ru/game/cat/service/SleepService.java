package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.callback.CallbackQueryExecutor;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Sleep;
import ru.game.cat.entity.Sticker;
import ru.game.cat.utils.ClockUtil;
import ru.game.cat.utils.Texts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.game.cat.enums.StickerNames.SLEEP_STICKER;
import static ru.game.cat.factory.CallbacksFactory.SLEEP_STREET_CALLBACK;
import static ru.game.cat.utils.Texts.*;

@Service
@RequiredArgsConstructor
public class SleepService implements CallbackQueryExecutor {

    private final CatService catService;
    private final StatisticService statisticService;
    private final MessageSender messageSender;
    private final StickersService stickersService;
    public Sleep getActualSleep(Cat cat) {
        Sleep sleep = cat.getSleep();
        if (sleep == null) {
            sleep = Sleep.builder()
                    .isSleep(false)
                    .amountSleeps(0)
                    .checkDate(LocalDateTime.now().minusYears(100))
                    .energyAfterSleep(50)
                    .minutesForSleep(120)
                    .build();
            cat.setSleep(sleep);
            catService.save(cat);
        }
        return sleep;
    }

    private void startSleep(@NonNull Cat cat) {
        var sleep = getActualSleep(cat);
        sleep.setSleep(true);
        sleep.setCheckDate(LocalDateTime.now().plusMinutes(sleep.getMinutesForSleep()));
        sleep.setAmountSleeps(sleep.getAmountSleeps() + 1);
        cat.setSleep(sleep);
        catService.save(cat);
    }


    private void finishSleep(@NonNull Cat cat) {
        var sleep = getActualSleep(cat);
        sleep.setSleep(false);
        statisticService.plusEnergy(cat, sleep.getEnergyAfterSleep());
        cat.setSleep(sleep);
        catService.save(cat);
    }


    public boolean isCheckSleep(@NonNull Cat cat) {
        if (catIsSleep(cat)) {
            return false;
        }
        if (sleepIsNotFinished(cat)) {
            return false;
        } else {
            return true;
        }
    }


    public boolean catIsSleep(@NonNull Cat cat) {
        return getActualSleep(cat).isSleep();
    }

    public boolean sleepIsNotFinished(@NonNull Cat cat) {
        var start = LocalDateTime.now();
        var sleep = getActualSleep(cat);
        var end = sleep.getCheckDate();

        long seconds = Duration.between(start, end).toSeconds();
        if (seconds <= 0L) {
            return false;
        }
        return seconds < (sleep.getMinutesForSleep() * 60L);

    }

    public void executeCommand(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        stickersService.executeSticker(update,stickersService.findById(SLEEP_STICKER));
        if (cat.getYard().isInTheWalk()) {
            messageSender.sendMessage(update.getMessage().getChatId(), CAT_WALK_IN_YARD_TEXT);
            return;
        }
        if (isCheckSleep(cat)) {
            initNotSleep(update);
        } else if (!sleepIsNotFinished(cat)) {
            finishSleep(cat);
            initNotSleep(update);
        } else {
            initTimeSleep(update, cat);
        }
    }

    private void initNotSleep(@NonNull Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder()
                .callbackData(SLEEP_STREET_CALLBACK)
                .text(Texts.SLEEP_STREET_BUTTON_TEXT)
                .build());
        rows.add(row1);
        markup.setKeyboard(rows);
        messageSender.sendMessageWithKeyboard(update.getMessage().getChatId(),
                SLEEP_STREET_INFO_TEXT,
                markup);
    }

    public void initTimeSleep(@NonNull Update update, Cat cat) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = getActualSleep(cat).getCheckDate();
        messageSender.sendMessage(update.getMessage().getChatId(),
                Texts.CAT_IS_SLEEP_TEXT + "\n" +
                        "Осталось спать \n" + ClockUtil.getHoursMinutesAndSeconds(start, end));
    }

    @Override
    public void executeCallback(Update update) {
        Cat cat = catService.findActualCat(update);
        if (cat.getStatistics().getEnergy() >= cat.getStatistics().getMaxEnergy()) {
            messageSender.sendMessageDialog(update, CAT_CANT_SLEEP_TEXT);
            return;
        }
        startSleep(cat);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = getActualSleep(cat).getCheckDate();
        messageSender.deleteMessage(update.getCallbackQuery().getMessage().getChatId(),
                update.getCallbackQuery().getMessage().getMessageId());
        messageSender.sendMessage(update.getCallbackQuery().getMessage().getChatId(),
                Texts.CAT_IS_SLEEP_TEXT + "\n" +
                        "Осталось спать \n" + ClockUtil.getHoursMinutesAndSeconds(start, end));
    }
}
