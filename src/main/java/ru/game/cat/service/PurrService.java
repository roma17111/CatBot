package ru.game.cat.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.game.cat.bot.emojy.Emojy;
import ru.game.cat.bot.message.MessageSender;
import ru.game.cat.entity.Cat;
import ru.game.cat.entity.Purr;
import ru.game.cat.enums.StickerNames;
import ru.game.cat.service.purr.PurrInventoryExecutor;
import ru.game.cat.utils.ClockUtil;
import ru.game.cat.utils.RandomUtils;
import ru.game.cat.utils.Texts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;
import static ru.game.cat.factory.CallbacksFactory.CAT_COIN_PURR_CALLBACK;
import static ru.game.cat.factory.CallbacksFactory.MILK_PURR_CALLBACK;
import static ru.game.cat.utils.Numbers.HUNDRED;

@Service
@RequiredArgsConstructor
public class PurrService {

    private static final int EIGHT = 8;

    private final MessageSender messageSender;
    private final CatService catService;
    private final StickersService stickersService;

    private Purr getActualPurr(@NonNull Update update) {
        Cat cat = catService.findActualCat(update);
        Purr purr = cat.getPurr();
        if (purr == null) {
            purr = Purr.builder()
                    .countPurrs(ZERO)
                    .checkDate(LocalDateTime.now().minusYears(HUNDRED))
                    .hoursInterval(EIGHT)
                    .maxPurrLoot(2)
                    .build();
            cat.setPurr(purr);
            catService.save(cat);
        }
        return purr;
    }

    private String getPurrTimeText(@NonNull Purr purr) {

        return Emojy.CAT_ERROR_EMOJY + "Ты уже помурчал\n" + Emojy.SMILE + "Приходи через\n" + Emojy.CLOCK_EMOJY +
                ClockUtil.getHoursMinutesAndSeconds(LocalDateTime.now(), purr.getCheckDate());

    }

    private void initPurr(@NonNull Cat cat, Update update) {
        Purr purr = cat.getPurr();
        purr.setCheckDate(LocalDateTime.now().plusHours(purr.getHoursInterval()));
        purr.setCountPurrs(purr.getCountPurrs() + 1);
        cat.setPurr(purr);
        catService.save(cat);
    }

    public void executeCommand(@NonNull Update update) {
        stickersService.executeSticker(update, stickersService.findById(StickerNames.PURR_COMMAND));
        Purr purr = getActualPurr(update);
        long chatId = update.getMessage().getChatId();
        if (purrAreReady(purr)) {
            messageSender.sendMessageWithKeyboard(chatId,
                    Texts.MURR_QUESTION,
                    getPurrKeyboard());
        } else {
            messageSender.sendMessage(chatId, getPurrTimeText(purr));
        }
    }

    public void executeCallback(@NonNull Update update,
                                @NonNull PurrInventoryExecutor purrInventoryExecutor) {

        Cat cat = catService.findActualCat(update);
        Purr purr = getActualPurr(update);
        if (!purrAreReady(purr)) {
            messageSender.sendMessageDialog(update, Emojy.CAT + "Подожди дружище. Ещё не время" + Emojy.SMILE);
            return;
        }
        if (cat.getYard().isInTheWalk()) {
            return;
        }
        initPurr(cat, update);
        StringBuilder builder = new StringBuilder(Texts.MURR_GET_TEXT).append("\n\n");
        long loot = cat.getPurr().getMaxPurrLoot();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        builder.append(purrInventoryExecutor.getPurrLoot(cat, RandomUtils.getRandomNumber(loot)));

        messageSender.deleteMessage(chatId,
                update.getCallbackQuery().getMessage().getMessageId());
        messageSender.sendMessage(chatId, builder.toString());

    }

    private InlineKeyboardMarkup getPurrKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        row1.add(InlineKeyboardButton.builder()
                .text(Emojy.MILK_EMOJY)
                .callbackData(MILK_PURR_CALLBACK)
                .build());

        row2.add(InlineKeyboardButton.builder()
                .text(Emojy.CAT_COINS)
                .callbackData(CAT_COIN_PURR_CALLBACK)
                .build());
        rows.add(row1);
        rows.add(row2);
        markup.setKeyboard(rows);
        return markup;
    }

    private boolean purrAreReady(Purr purr) {
        System.out.println(purr);
        if (purr == null) {
            return true;
        }
        var start = LocalDateTime.now();
        var end = purr.getCheckDate();
        long seconds = Duration.between(start, end).toSeconds();
        if (seconds <= 0) {
            return true;
        }
        long l = purr.getHoursInterval() * 60L * 60L;
        return seconds > (l);
    }


}
